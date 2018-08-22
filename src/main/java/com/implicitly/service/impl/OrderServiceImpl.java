/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.domain.order.Order;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.persistence.order.OrderRepository;
import com.implicitly.service.OrderService;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.order.OrderMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * @return список {@link OrderDTO}
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> result = repository.findAll();
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
    public OrderDTO getOrder(Long id) {
        Order order = repository.findOne(id);
        if (order == null) {
            return null;
        }
        return mapper.toDto(order);
    }

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    @Override
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
     */
    @Override
    public void updateOrder(Long id, OrderDTO order) {
        if (repository.exists(id)) {
            Order entity = mapper.toEntity(order);
            entity.setModifiedDate(LocalDateTime.now());
            UserUtils.getCurrentUser().ifPresent(entity::setModifiedBy);
            repository.save(entity);
        }
    }

    /**
     * Удаление {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    public void deleteOrder(Long id) {
        if (repository.exists(id)) {
            repository.delete(id);
        }
    }

}
