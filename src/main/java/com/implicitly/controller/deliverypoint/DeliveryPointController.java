/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller.deliverypoint;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.service.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * Реализация {@link RestController}, обслуживающая запросы к сущности {@link DeliveryPointDTO}.
 *
 * @author Emil Murzakaev.
 */
@RestController
public class DeliveryPointController {

    /**
     * {@link DeliveryPointService}
     */
    private final DeliveryPointService deliveryPointService;

    /**
     * Конструктор.
     *
     * @param deliveryPointService {@link DeliveryPointService}
     */
    @Autowired
    public DeliveryPointController(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

    /**
     * Получение всех сущностей {@link DeliveryPointDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link DeliveryPointDTO}
     */
    @GetMapping(value = "/delivery-point", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<DeliveryPointDTO>> getAllDeliveryPoints(Pageable pageable) {
        return ok(deliveryPointService.getAllDeliveryPoints(pageable));
    }

    /**
     * Получение {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link DeliveryPointDTO}.
     */
    @GetMapping(value = "/delivery-point/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeliveryPointDTO> getDeliveryPoint(@PathVariable("id") Long id) {
        return ok(deliveryPointService.getDeliveryPoint(id));
    }

    /**
     * Сохранение {@link DeliveryPointDTO}
     *
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @PostMapping(value = "/delivery-point", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeliveryPointDTO> saveDeliveryPoint(@RequestBody DeliveryPointDTO deliveryPoint) {
        return ok(deliveryPointService.saveDeliveryPoint(deliveryPoint));
    }

    /**
     * Редактирование {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @PutMapping(value = "/delivery-point/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateDeliveryPoint(
            @PathVariable("id") Long id, @RequestBody DeliveryPointDTO deliveryPoint) {
        deliveryPointService.updateDeliveryPoint(id, deliveryPoint);
        return noContent().build();
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

}
