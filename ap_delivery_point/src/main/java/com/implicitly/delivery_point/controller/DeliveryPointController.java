/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.delivery_point.controller;

import com.implicitly.delivery_point.service.DeliveryPointService;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
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
 * Реализация {@link RestController}, обслуживающая запросы к сущности {@link DeliveryPointDTO}.
 *
 * @author Emil Murzakaev.
 */
@RestController
@RequiredArgsConstructor
public class DeliveryPointController {

    /**
     * {@link DeliveryPointService}
     */
    private final DeliveryPointService deliveryPointService;

    /**
     * Получение всех сущностей {@link DeliveryPointDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link DeliveryPointDTO}
     */
    @GetMapping(value = "/delivery-point")
    public ResponseEntity<Page<DeliveryPointDTO>> getAllDeliveryPoints(Pageable pageable) {
        return ok(deliveryPointService.getAllDeliveryPoints(pageable));
    }

    /**
     * Получение {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link DeliveryPointDTO}.
     */
    @GetMapping(value = "/delivery-point/{id}")
    public ResponseEntity<DeliveryPointDTO> getDeliveryPoint(@PathVariable("id") Long id) {
        return ok(deliveryPointService.getDeliveryPoint(id));
    }

    /**
     * Сохранение {@link DeliveryPointDTO}
     *
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @PostMapping(value = "/delivery-point")
    public ResponseEntity<DeliveryPointDTO> saveDeliveryPoint(@RequestBody DeliveryPointDTO deliveryPoint) {
        return ok(deliveryPointService.saveDeliveryPoint(deliveryPoint));
    }

    /**
     * Редактирование {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id            уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @PutMapping(value = "/delivery-point/{id}")
    public ResponseEntity<DeliveryPointDTO> updateDeliveryPoint(
            @PathVariable("id") Long id, @RequestBody DeliveryPointDTO deliveryPoint) {
        return ok(deliveryPointService.updateDeliveryPoint(id, deliveryPoint));
    }

    /**
     * Удаление {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @DeleteMapping(value = "/delivery-point/{id}")
    public ResponseEntity<Void> deleteDeliveryPoint(@PathVariable("id") Long id) {
        deliveryPointService.deleteDeliveryPoint(id);
        return noContent().build();
    }

    /**
     * Поиск {@link DeliveryPointDTO} по фильтру.
     *
     * @param searchFilter фильтр.
     * @param pageable     {@link Pageable}.
     * @return {@link Page<DeliveryPointDTO>}.
     */
    @PostMapping(value = "/delivery-point/search")
    public ResponseEntity<Page<DeliveryPointDTO>> search(@RequestBody DeliveryPointDTO searchFilter, Pageable pageable) {
        return ok(deliveryPointService.search(searchFilter, pageable));
    }

}
