/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.order.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.domain.customer.Customer;
import com.implicitly.domain.order.Order;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.exception.Error;
import com.implicitly.order.persistence.OrderRepository;
import com.implicitly.order.service.OrderService;
import com.implicitly.utils.UserUtils;
import com.implicitly.utils.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Реализация сервиса работы с сущностью {@link OrderDTO}
 *
 * @author Emil Murzakaev.
 */
@Service
@RequiredArgsConstructor
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
    @Cacheable(value = Constants.CACHE_ORDER, key = "#p0")
    public OrderDTO getOrder(Long id) {
        Order order = repository.findById(id).orElseThrow(Error.ORDER_NOT_FOUND::exception);
        return mapper.toDto(order);
    }

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    @Override
    @Caching(
            evict = @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true),
            put = @CachePut(value = Constants.CACHE_ORDER, key = "#result.id")
    )
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
     * @param id    уникальный идентификатор.
     * @param order {@link OrderDTO}.
     * @return {@link OrderDTO}.
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Constants.CACHE_ORDER, key = "#p0"),
                    @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true)
            },
            put = @CachePut(value = Constants.CACHE_ORDER, key = "#result.id")
    )
    public OrderDTO updateOrder(Long id, OrderDTO order) {
        Error.ORDER_NOT_FOUND.throwIfFalse(repository.existsById(id));
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
    @Caching(
            evict = {
                    @CacheEvict(value = Constants.CACHE_ORDER, key = "#p0"),
                    @CacheEvict(value = Constants.CACHE_ORDERS, allEntries = true)
            }
    )
    public void deleteOrder(Long id) {
        Error.ORDER_NOT_FOUND.throwIfFalse(repository.existsById(id));
        repository.deleteById(id);
    }

    /**
     * Получение списка {@link OrderDTO}, относящихся к определенному заказчику.
     *
     * @param customerId идентификатор заказчика.
     * @param pageable {@link Pageable}
     */
    @Override
    public Page<OrderDTO> getCustomerOrders(Long customerId, Pageable pageable) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return repository.findAllByCustomer(customer, pageable).map(mapper::toDto);
    }

}
