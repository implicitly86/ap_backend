/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service;

import com.implicitly.dto.order.OrderDTO;
import java.util.List;

/**
 * Сервис работы с сущностью {@link OrderDTO}
 *
 * @author Emil Murzakaev.
 */
public interface OrderService {

    /**
     * Получение всех сущностей {@link OrderDTO}.
     *
     * @return список {@link OrderDTO}
     */
    List<OrderDTO> getAllOrders();

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
     * @param id уникальный идентификатор.
     * @param order {@link OrderDTO}.
     */
    void updateOrder(Long id, OrderDTO order);

    /**
     * Удаление {@link OrderDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    void deleteOrder(Long id);

}
