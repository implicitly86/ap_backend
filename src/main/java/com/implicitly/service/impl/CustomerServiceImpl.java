/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.domain.customer.Customer;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.persistence.customer.CustomerRepository;
import com.implicitly.service.CustomerService;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.customer.CustomerMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * {@link CustomerMapper}
     */
    private final CustomerMapper mapper;

    /**
     * Конструктор.
     *
     * @param repository {@link CustomerRepository}.
     * @param mapper {@link CustomerMapper}.
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Получение всех сущностей {@link CustomerDTO}.
     *
     * @return список {@link CustomerDTO}
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> result = repository.findAll();
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result.stream().map(mapper::toDto).collect(Collectors.toList());
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
            return null;
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
        if (repository.exists(id)) {
            Customer entity = mapper.toEntity(customer);
            repository.save(entity);
        }
    }

    /**
     * Удаление {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    public void deleteCustomer(Long id) {
        if (repository.exists(id)) {
            repository.delete(id);
        }
    }

}
