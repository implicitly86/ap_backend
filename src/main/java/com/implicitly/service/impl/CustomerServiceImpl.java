/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.domain.customer.Customer;
import com.implicitly.domain.customer.Customer_;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.exceptions.NotFoundException;
import com.implicitly.persistence.customer.CustomerRepository;
import com.implicitly.persistence.order.OrderRepository;
import com.implicitly.service.CustomerService;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.customer.CustomerMapper;
import com.implicitly.utils.mapper.order.OrderMapper;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Реализация сервиса работы с сущностью {@link CustomerDTO}
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    /**
     * {@link CustomerRepository}
     */
    private final CustomerRepository repository;

    /**
     * {@link OrderRepository}
     */
    private final OrderRepository orderRepository;

    /**
     * {@link CustomerMapper}
     */
    private final CustomerMapper mapper;

    /**
     * {@link OrderMapper}
     */
    private final OrderMapper orderMapper;

    /**
     * Конструктор.
     *
     * @param repository {@link CustomerRepository}.
     * @param mapper {@link CustomerMapper}.
     * @param orderRepository {@link OrderRepository}.
     * @param orderMapper {@link OrderMapper}.
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper,
            OrderRepository orderRepository, OrderMapper orderMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Получение всех сущностей {@link CustomerDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link CustomerDTO}
     */
    @Override
    @Cacheable(value = Constants.CACHE_CUSTOMERS)
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    /**
     * Получение {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link CustomerDTO}.
     */
    @Override
    public CustomerDTO getCustomer(Long id) {
        Customer customer = repository.findOne(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        return mapper.toDto(customer);
    }

    /**
     * Сохранение {@link CustomerDTO}
     *
     * @param customer {@link CustomerDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true)
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        Customer entity = mapper.toEntity(customer);
        entity.setCreatedDate(LocalDateTime.now());
        UserUtils.getCurrentUser().ifPresent(entity::setAuthor);
        Customer result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Редактирование {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param customer {@link CustomerDTO}.
     * @return {@link CustomerDTO}
     */
    @Override
    @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true)
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
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
    @CacheEvict(value = Constants.CACHE_CUSTOMERS, allEntries = true)
    public void deleteCustomer(Long id) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        repository.delete(id);
    }

    /**
     * Получение списка {@link OrderDTO} по идентификатору автора, разбитых по {@link Page}.
     *
     * @param id уникальный идентификатор {@link CustomerDTO}.
     * @param pageable {@link Pageable}.
     * @return {@link Page<OrderDTO>}.
     */
    @Override
    public Page<OrderDTO> getOrders(Long id, Pageable pageable) {
        Customer customer = repository.findOne(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        return orderRepository.findAllByCustomer(customer, pageable).map(orderMapper::toDto);
    }

    /**
     * Поиск {@link CustomerDTO} по фильтру.
     *
     * @param searchFilter фильтр.
     * @param pageable {@link Pageable}.
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
