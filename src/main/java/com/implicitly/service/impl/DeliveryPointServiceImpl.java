/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.persistence.deliverypoint.DeliveryPointRepository;
import com.implicitly.service.DeliveryPointService;
import com.implicitly.utils.mapper.deliverypoint.DeliveryPointMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Реализация сервиса работы с сущностью {@link DeliveryPointDTO}
 *
 * @author Emil Murzakaev.
 */
@Service
public class DeliveryPointServiceImpl implements DeliveryPointService {

    /**
     * {@link DeliveryPointRepository}
     */
    private final DeliveryPointRepository repository;

    /**
     * {@link DeliveryPointMapper}
     */
    private final DeliveryPointMapper mapper;

    /**
     * Конструктор.
     *
     * @param repository {@link DeliveryPointRepository}.
     * @param mapper {@link DeliveryPointMapper}.
     */
    @Autowired
    public DeliveryPointServiceImpl(DeliveryPointRepository repository, DeliveryPointMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Получение всех сущностей {@link DeliveryPointDTO}.
     *
     * @return список {@link DeliveryPointDTO}
     */
    @Override
    public List<DeliveryPointDTO> getAllDeliveryPoints() {
        List<DeliveryPoint> result = repository.findAll();
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Получение {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link DeliveryPointDTO}.
     */
    @Override
    public DeliveryPointDTO getDeliveryPoint(Long id) {
        DeliveryPoint deliveryPoint = repository.findOne(id);
        if (deliveryPoint == null) {
            return null;
        }
        return mapper.toDto(deliveryPoint);
    }

    /**
     * Сохранение {@link DeliveryPointDTO}
     *
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @Override
    public DeliveryPointDTO saveDeliveryPoint(DeliveryPointDTO deliveryPoint) {
        DeliveryPoint entity = repository.saveAndFlush(mapper.toEntity(deliveryPoint));
        return mapper.toDto(entity);
    }

    /**
     * Редактирование {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @Override
    public void updateDeliveryPoint(Long id, DeliveryPointDTO deliveryPoint) {
        if (repository.exists(id)) {
            DeliveryPoint entity = mapper.toEntity(deliveryPoint);
            repository.save(entity);
        }
    }

    /**
     * Удаление {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    public void deleteDeliveryPoint(Long id) {
        if (repository.exists(id)) {
            repository.delete(id);
        }
    }

}
