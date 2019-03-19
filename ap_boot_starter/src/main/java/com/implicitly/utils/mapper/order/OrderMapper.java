/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.mapper.order;

import com.implicitly.domain.order.Order;
import com.implicitly.dto.order.OrderDTO;
import com.implicitly.utils.mapper.EntityMapper;
import com.implicitly.utils.mapper.customer.CustomerMapper;
import com.implicitly.utils.mapper.deliverypoint.DeliveryPointMapper;
import com.implicitly.utils.mapper.security.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link EntityMapper} для сущности {@link Order}
 *
 * @author Emil Murzakaev.
 */
@Component
@RequiredArgsConstructor
public class OrderMapper implements EntityMapper<Order, OrderDTO> {

    /**
     * {@link UserMapper}
     */
    private final UserMapper userMapper;

    /**
     * {@link DeliveryPointMapper}
     */
    private final DeliveryPointMapper deliveryPointMapper;

    /**
     * {@link CustomerMapper}
     */
    private final CustomerMapper customerMapper;

    /**
     * Маппинг Entity -> Dto.
     *
     * @param source доменная сущность.
     * @return модель передачи данных, соответствующая доменной модели.
     */
    @Override
    public OrderDTO toDto(Order source) {
        if (source == null) {
            return null;
        }
        OrderDTO target = OrderDTO.builder().build();
        BeanUtils.copyProperties(source, target);
        target.setAuthor(userMapper.toDto(source.getAuthor()));
        target.setModifiedBy(userMapper.toDto(source.getModifiedBy()));
        target.setFromPoint(deliveryPointMapper.toDto(source.getFromPoint()));
        target.setToPoint(deliveryPointMapper.toDto(source.getToPoint()));
        target.setCustomer(customerMapper.toDto(source.getCustomer()));
        return target;
    }

    /**
     * Маппинг Dto -> Entity.
     *
     * @param source модель передачи данных, соответствующая доменной модели.
     * @return доменная сущность.
     */
    @Override
    public Order toEntity(OrderDTO source) {
        if (source == null) {
            return null;
        }
        Order target = Order.builder().build();
        BeanUtils.copyProperties(source, target);
        target.setAuthor(userMapper.toEntity(source.getAuthor()));
        target.setModifiedBy(userMapper.toEntity(source.getModifiedBy()));
        target.setFromPoint(deliveryPointMapper.toEntity(source.getFromPoint()));
        target.setToPoint(deliveryPointMapper.toEntity(source.getToPoint()));
        target.setCustomer(customerMapper.toEntity(source.getCustomer()));
        return target;
    }

}
