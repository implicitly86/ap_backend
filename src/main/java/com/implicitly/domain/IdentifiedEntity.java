/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain;

import java.io.Serializable;

/**
 * Идентифицируемая сущность.
 *
 * @author Emil Murzakaev.
 */
public interface IdentifiedEntity extends Serializable {

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
