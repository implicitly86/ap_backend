/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.domain.order.Order;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.exceptions.NotFoundException;
import com.implicitly.persistence.order.OrderRepository;
import com.implicitly.service.OrderService;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.order.OrderMapper;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса работы с сущностью {@link OrderDTO}
 *
 * @author Emil Murzakaev.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * {@link OrderRepository}
     */
    private final OrderRepository repository;

    /**
     * {@link OrderMapper}
     */
    private final OrderMapper mapper;

    /**
     * Конструктор.
     *
     * @param repository {@link OrderRepository}.
     * @param mapper {@link OrderMapper}.
     */
    @Autowired
    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Получение всех сущностей {@link OrderDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link OrderDTO}
     */
    @Override
    @Cacheable(value = Constants.CACHE_ORDERS)
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    /**
     * Получение {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link CustomerDTO}.
     */
    @Override
    public OrderDTO getOrder(Long id) {
        Order order = repository.findOne(id);
        if (order == null) {
            throw new NotFoundException();
        }
        return mapper.toDto(order);
    }

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true)
    public OrderDTO saveOrder(OrderDTO order) {
        Order entity = mapper.toEntity(order);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setModifiedDate(LocalDateTime.now());
        UserUtils.getCurrentUser().ifPresent(it -> {
            entity.setAuthor(it);
            entity.setModifiedBy(it);
        });
        Order result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Редактирование {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param order {@link OrderDTO}.
     * @return {@link OrderDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true)
    public OrderDTO updateOrder(Long id, OrderDTO order) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        Order entity = mapper.toEntity(order);
        entity.setModifiedDate(LocalDateTime.now());
        UserUtils.getCurrentUser().ifPresent(entity::setModifiedBy);
        Order result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Удаление {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true)
    public void deleteOrder(Long id) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        repository.delete(id);
    }

}
