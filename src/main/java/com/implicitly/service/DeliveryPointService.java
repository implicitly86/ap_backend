/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service;

import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис работы с сущностью {@link DeliveryPointDTO}
 *
 * @author Emil Murzakaev.
 */
public interface DeliveryPointService {

    /**
     * Получение всех сущностей {@link DeliveryPointDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link DeliveryPointDTO}
     */
    Page<DeliveryPointDTO> getAllDeliveryPoints(Pageable pageable);

    /**
     * Получение {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link DeliveryPointDTO}.
     */
    DeliveryPointDTO getDeliveryPoint(Long id);

    /**
     * Сохранение {@link DeliveryPointDTO}
     *
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    DeliveryPointDTO saveDeliveryPoint(DeliveryPointDTO deliveryPoint);

    /**
     * Редактирование {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    void updateDeliveryPoint(Long id, DeliveryPointDTO deliveryPoint);

    /**
     * Удаление {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    void deleteDeliveryPoint(Long id);

}
