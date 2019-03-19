/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.mapper.deliverypoint;

import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.utils.mapper.EntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link EntityMapper} для сущности {@link DeliveryPoint}
 *
 * @author Emil Murzakaev.
 */
@Component
public class DeliveryPointMapper implements EntityMapper<DeliveryPoint, DeliveryPointDTO> {

    /**
     * Маппинг Entity -> Dto.
     *
     * @param source доменная сущность.
     * @return модель передачи данных, соответствующая доменной модели.
     */
    @Override
    public DeliveryPointDTO toDto(DeliveryPoint source) {
        if (source == null) {
            return null;
        }
        DeliveryPointDTO target = DeliveryPointDTO.builder().build();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * Маппинг Dto -> Entity.
     *
     * @param source модель передачи данных, соответствующая доменной модели.
     * @return доменная сущность.
     */
    @Override
    public DeliveryPoint toEntity(DeliveryPointDTO source) {
        if (source == null) {
            return null;
        }
        DeliveryPoint target = DeliveryPoint.builder().build();
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
