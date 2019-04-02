/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.order.service;

import com.implicitly.dto.order.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис работы с сущностью {@link OrderDTO}
 *
 * @author Emil Murzakaev.
 */
public interface OrderService {

    /**
     * Получение всех сущностей {@link OrderDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link OrderDTO}
     */
    Page<OrderDTO> getAllOrders(Pageable pageable);

    /**
     * Получение {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link OrderDTO}.
     */
    OrderDTO getOrder(Long id);

    /**
     * Сохранение {@link OrderDTO}
     *
     * @param order {@link OrderDTO}.
     */
    OrderDTO saveOrder(OrderDTO order);

    /**
     * Редактирование {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id    уникальный идентификатор.
     * @param order {@link OrderDTO}.
     * @return {@link OrderDTO}.
     */
    OrderDTO updateOrder(Long id, OrderDTO order);

    /**
     * Удаление {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    void deleteOrder(Long id);

    /**
     * Получение списка {@link OrderDTO}, относящихся к определенному заказчику.
     *
     * @param customerId идентификатор заказчика.
     * @param pageable {@link Pageable}
     */
    Page<OrderDTO> getCustomerOrders(Long customerId, Pageable pageable);

}
