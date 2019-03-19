/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.delivery_point.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.delivery_point.persistence.DeliveryPointRepository;
import com.implicitly.delivery_point.service.DeliveryPointService;
import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.utils.mapper.deliverypoint.DeliveryPointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса работы с сущностью {@link DeliveryPointDTO}
 *
 * @author Emil Murzakaev.
 */
@Slf4j
@Service
@RequiredArgsConstructor
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
     * Получение всех сущностей {@link DeliveryPointDTO}.
     *
     * @param pageable {@link Pageable}
     * @return список {@link DeliveryPointDTO}
     */
    @Override
    @Cacheable(value = Constants.CACHE_DELIVERY_POINTS)
    public Page<DeliveryPointDTO> getAllDeliveryPoints(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    /**
     * Получение {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link DeliveryPointDTO}.
     */
    @Override
    public DeliveryPointDTO getDeliveryPoint(Long id) {
        DeliveryPoint deliveryPoint = repository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.toDto(deliveryPoint);
    }

    /**
     * Сохранение {@link DeliveryPointDTO}
     *
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_DELIVERY_POINTS, allEntries = true)
    public DeliveryPointDTO saveDeliveryPoint(DeliveryPointDTO deliveryPoint) {
        DeliveryPoint entity = repository.saveAndFlush(mapper.toEntity(deliveryPoint));
        return mapper.toDto(entity);
    }

    /**
     * Редактирование {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id            уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     * @return {@link DeliveryPointDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_DELIVERY_POINTS, allEntries = true)
    public DeliveryPointDTO updateDeliveryPoint(Long id, DeliveryPointDTO deliveryPoint) {
        if (!repository.existsById(id)) {
            throw new RuntimeException();
        }
        DeliveryPoint entity = mapper.toEntity(deliveryPoint);
        DeliveryPoint result = repository.saveAndFlush(entity);
        return mapper.toDto(result);
    }

    /**
     * Удаление {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_DELIVERY_POINTS, allEntries = true)
    public void deleteDeliveryPoint(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException();
        }
        repository.deleteById(id);
    }

    /**
     * Поиск {@link DeliveryPointDTO} по фильтру.
     *
     * @param searchFilter фильтр.
     * @param pageable     {@link Pageable}.
     * @return {@link Page<DeliveryPointDTO>}.
     */
    @Override
    public Page<DeliveryPointDTO> search(DeliveryPointDTO searchFilter, Pageable pageable) {
        /*
        Specifications<DeliveryPoint> specifications = Specifications.where(null);
        if (searchFilter.getId() != null) {
            specifications = specifications.and((root, query, cb) ->
                    cb.equal(root.get(DeliveryPoint_.id), searchFilter.getId())
            );
        }
        if (!StringUtils.isEmpty(searchFilter.getName())) {
            specifications = specifications.and((root, query, cb) ->
                    cb.like(cb.lower(root.get(DeliveryPoint_.name)), "%" + searchFilter.getName().toLowerCase() + "%")
            );
        }
        return repository.findAll(specifications, pageable).map(mapper::toDto);
        */
        return null;
    }

}
