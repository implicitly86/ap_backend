/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.order.controller;

import com.implicitly.dto.order.OrderDTO;
import com.implicitly.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Реализация {@link RestController}, обслуживающая запросы к сущности {@link OrderDTO}.
 *
 * @author Emil Murzakaev.
 */
@RestController
@RequiredArgsConstructor
public class OrderController {

    /**
     * {@link OrderService}
     */
    private final OrderService orderService;

    /**
     * Получение всех сущностей {@link OrderDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link com.implicitly.dto.order.OrderDTO}
     */
    @GetMapping(value = "/order")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        return ok(orderService.getAllOrders(pageable));
    }

    /**
     * Получение {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link OrderDTO}.
     */
    @GetMapping(value = "/order/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") Long id) {
        return ok(orderService.getOrder(id));
    }

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    @PostMapping(value = "/order")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO order) {
        return ok(orderService.saveOrder(order));
    }

    /**
     * Редактирование {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id    уникальный идентификатор.
     * @param order {@link OrderDTO}.
     */
    @PutMapping(value = "/order/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO order) {
        return ok(orderService.updateOrder(id, order));
    }

    /**
     * Удаление {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return noContent().build();
    }

    /**
     * Получение списка {@link OrderDTO}, относящихся к определенному заказчику.
     *
     * @param customerId идентификатор заказчика.
     * @param pageable {@link Pageable}
     */
    @GetMapping(value = "/order/customer/{customerId}")
    public ResponseEntity<Page<OrderDTO>> getOrders(@PathVariable("customerId") Long customerId, Pageable pageable) {
        return ok(orderService.getCustomerOrders(customerId, pageable));
    }

}
