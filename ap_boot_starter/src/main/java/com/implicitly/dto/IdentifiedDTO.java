/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto;

import java.io.Serializable;

/**
 * Модель передачи данных идентифицируемой сущности.
 *
 * @author Emil Murzakaev.
 */
public interface IdentifiedDTO extends Serializable {

    /**
     * Получить идентификатор сущности.
     *
     * @return идентификатор сущности.
     */
    Long getId();

    /**
     * Задать идентификатор сущности.
     *
     * @param id идентификатор сущности.
     */
    void setId(Long id);

}
