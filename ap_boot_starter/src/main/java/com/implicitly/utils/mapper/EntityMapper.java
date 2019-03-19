/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.mapper;

import java.io.Serializable;

/**
 * Маппер сущностей.
 *
 * @author Emil Murzakaev.
 */
public interface EntityMapper<Entity extends Serializable, Dto extends Serializable> {

    /**
     * Маппинг Entity -> Dto.
     *
     * @param source доменная сущность.
     * @return модель передачи данных, соответствующая доменной модели.
     */
    Dto toDto(Entity source);

    /**
     * Маппинг Dto -> Entity.
     *
     * @param source модель передачи данных, соответствующая доменной модели.
     * @return доменная сущность.
     */
    Entity toEntity(Dto source);

}
