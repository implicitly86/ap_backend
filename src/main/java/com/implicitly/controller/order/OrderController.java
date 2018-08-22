/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller.order;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import com.implicitly.dto.order.OrderDTO;
import com.implicitly.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Реализация {@link RestController}, обслуживающая запросы к сущности {@link OrderDTO}.
 *
 * @author Emil Murzakaev.
 */
@RestController
public class OrderController {

    /**
     * {@link OrderService}
     */
    private final OrderService orderService;

    /**
     * Конструктор.
     *
     * @param orderService {@link OrderService}
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Получение всех сущностей {@link com.implicitly.dto.order.OrderDTO}.
     *
     * @return список {@link com.implicitly.dto.order.OrderDTO}
     */
    @GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ok(orderService.getAllOrders());
    }

    /**
     * Получение {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link OrderDTO}.
     */
    @GetMapping(value = "/order/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") Long id) {
        OrderDTO order = orderService.getOrder(id);
        if (order == null) {
            return notFound().build();
        }
        return ok(order);
    }

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO order) {
        return ok(orderService.saveOrder(order));
    }

    /**
     * Редактирование {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param order {@link OrderDTO}.
     */
    @PutMapping(value = "/order/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateOrder(
            @PathVariable("id") Long id, @RequestBody OrderDTO order) {
        orderService.updateOrder(id, order);
        return noContent().build();
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

}
