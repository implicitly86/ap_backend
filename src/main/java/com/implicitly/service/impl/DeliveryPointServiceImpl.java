/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service.impl;

import com.implicitly.constants.Constants;
import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.domain.deliverypoint.DeliveryPoint_;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import com.implicitly.exceptions.NotFoundException;
import com.implicitly.persistence.deliverypoint.DeliveryPointRepository;
import com.implicitly.service.DeliveryPointService;
import com.implicitly.utils.mapper.deliverypoint.DeliveryPointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        DeliveryPoint deliveryPoint = repository.findOne(id);
        if (deliveryPoint == null) {
            throw new NotFoundException();
        }
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
     * @param id уникальный идентификатор.
     * @param deliveryPoint {@link DeliveryPointDTO}.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_DELIVERY_POINTS, allEntries = true)
    public void updateDeliveryPoint(Long id, DeliveryPointDTO deliveryPoint) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        DeliveryPoint entity = mapper.toEntity(deliveryPoint);
        repository.save(entity);
    }

    /**
     * Удаление {@link DeliveryPointDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    @Override
    @CacheEvict(value = Constants.CACHE_DELIVERY_POINTS, allEntries = true)
    public void deleteDeliveryPoint(Long id) {
        if (!repository.exists(id)) {
            throw new NotFoundException();
        }
        repository.delete(id);
    }

    /**
     * Поиск {@link DeliveryPointDTO} по фильтру.
     *
     * @param searchFilter фильтр.
     * @param pageable {@link Pageable}.
     * @return {@link Page<DeliveryPointDTO>}.
     */
    @Override
    public Page<DeliveryPointDTO> search(DeliveryPointDTO searchFilter, Pageable pageable) {
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
    }

}
