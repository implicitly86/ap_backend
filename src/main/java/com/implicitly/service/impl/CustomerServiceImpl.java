/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.domain.customer.Customer;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
     */
    @Override
    public void updateCustomer(Long id, CustomerDTO customer) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        Customer entity = mapper.toEntity(customer);
        repository.save(entity);
    }

    /**
     * Удаление {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
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

}
