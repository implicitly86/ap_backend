/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.customer.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.customer.feign.OrderService;
import com.implicitly.customer.persistence.CustomerRepository;
import com.implicitly.customer.service.CustomerService;
import com.implicitly.customer.validation.CustomerValidator;
import com.implicitly.domain.customer.Customer;
import com.implicitly.domain.customer.Customer_;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.exception.Error;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.customer.CustomerMapper;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация сервиса работы с сущностью {@link CustomerDTO}
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    /**
     * {@link CustomerValidator}
     */
    private static final CustomerValidator customerValidator = new CustomerValidator();

    /**
     * {@link CustomerRepository}
     */
    private final CustomerRepository repository;
    /**
     * {@link OrderService}
     */
    private final OrderService orderService;
    /**
     * {@link CustomerMapper}
     */
    private final CustomerMapper mapper;

    /**
     * Получение всех сущностей {@link CustomerDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link CustomerDTO}
     */
    @Override
    @Cacheable(value = Constants.CACHE_CUSTOMERS)
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        log.debug("invoke getAllCustomers({})", pageable);
        Page<CustomerDTO> result = repository.findAll(pageable).map(mapper::toDto);
        log.debug("result getAllCustomers({}) : {}", pageable, result);
        return result;
    }

    /**
     * Получение {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link CustomerDTO}.
     */
    @Override
    @Cacheable(value = Constants.CACHE_CUSTOMER, key = "#p0")
    public CustomerDTO getCustomer(Long id) {
        log.debug("invoke getCustomer({})", id);
        Customer customer = repository.findById(id).orElseThrow(Error.CUSTOMER_NOT_FOUND::exception);
        CustomerDTO result = mapper.toDto(customer);
        log.debug("result getCustomer({}) : {}", id, result);
        return result;
    }

    /**
     * Сохранение {@link CustomerDTO}
     *
     * @param customer {@link CustomerDTO}.
     */
    @Override
    @Caching(
            evict = @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true),
            put = @CachePut(value = Constants.CACHE_CUSTOMER, key = "#result.id")
    )
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        Validation<Seq<String>, CustomerDTO> validatedCustomer = customerValidator.validateCustomer(customer);
        if (validatedCustomer.isInvalid()) {
            Error error = Error.CUSTOMER_NOT_VALID;
            error.addData(validatedCustomer.getError());
            error.throwException();
        }
        Customer entity = mapper.toEntity(validatedCustomer.get());
        entity.setCreatedDate(LocalDateTime.now());
        UserUtils.getCurrentUser().ifPresent(entity::setAuthor);
        Customer result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Редактирование {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id       уникальный идентификатор.
     * @param customer {@link CustomerDTO}.
     * @return {@link CustomerDTO}
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Constants.CACHE_CUSTOMER, key = "#p0"),
                    @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true)
            },
            put = @CachePut(value = Constants.CACHE_CUSTOMER, key = "#result.id")
    )
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        Error.CUSTOMER_NOT_FOUND.throwIfFalse(repository.existsById(id));
        Customer entity = mapper.toEntity(customer);
        Customer result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Удаление {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Constants.CACHE_CUSTOMER, key = "#p0"),
                    @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true)
            }
    )
    public void deleteCustomer(Long id) {
        Error.CUSTOMER_NOT_FOUND.throwIfFalse(repository.existsById(id));
        repository.deleteById(id);
    }

    /**
     * Получение списка {@link OrderDTO} по идентификатору автора, разбитых по {@link Page}.
     *
     * @param id       уникальный идентификатор {@link CustomerDTO}.
     * @param pageable {@link Pageable}.
     * @return {@link Page<OrderDTO>}.
     */
    @Override
    public Page<OrderDTO> getOrders(Long id, Pageable pageable) {
        Customer customer = repository.findById(id).orElseThrow(Error.CUSTOMER_NOT_FOUND::exception);
        return orderService.getOrders(customer.getId(), pageable).getBody();
    }

    /**
     * Поиск {@link CustomerDTO} по фильтру.
     *
     * @param searchFilter фильтр.
     * @param pageable     {@link Pageable}.
     * @return {@link Page<CustomerDTO>}.
     */
    @Override
    public Page<CustomerDTO> search(CustomerDTO searchFilter, Pageable pageable) {
        Specifications<Customer> specifications = Specifications.where(null);
        if (searchFilter.getId() != null) {
            specifications = specifications.and((root, query, cb) ->
                    cb.equal(root.get(Customer_.id), searchFilter.getId())
            );
        }
        if (!StringUtils.isEmpty(searchFilter.getName())) {
            Pattern pattern = Pattern.compile("([\\wА-я]+)?( ([\\wА-я]+))?( ([\\wА-я]+))?$");
            Matcher matcher = pattern.matcher(searchFilter.getName());
            Specifications<Customer> specification = null;
            if (matcher.find()) {
                specification = Specifications.where(null);
                if (!StringUtils.isEmpty(matcher.group(1))) {
                    specification = specification.and((root, query, cb) ->
                            cb.like(cb.lower(root.get(Customer_.lastName)), "%" + matcher.group(1).toLowerCase() + "%")
                    );
                }
                if (!StringUtils.isEmpty(matcher.group(3))) {
                    specification = specification.and((root, query, cb) ->
                            cb.like(cb.lower(root.get(Customer_.firstName)), "%" + matcher.group(3).toLowerCase() + "%")
                    );
                }
                if (!StringUtils.isEmpty(matcher.group(5))) {
                    specification = specification.and((root, query, cb) ->
                            cb.like(cb.lower(root.get(Customer_.middleName)), "%" + matcher.group(5).toLowerCase() + "%")
                    );
                }
            }
            Specifications<Customer> nameSpecification = Specifications.where((root, query, cb) ->
                    cb.like(cb.lower(root.get(Customer_.name)), "%" + searchFilter.getName().toLowerCase() + "%")
            );
            if (specification != null) {
                specifications = specifications.and(nameSpecification.or(specification));
            } else {
                specifications = specifications.and(nameSpecification);
            }
        }
        return repository.findAll(specifications, pageable).map(mapper::toDto);
    }

}
