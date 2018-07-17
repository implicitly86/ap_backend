/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель передачи данных идентифицируемой сущности.
 *
 * @author Emil Murzakaev.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IdentifiedDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1332929768727127025L;

    /**
     * Идентификатор сущности.
     */
    Long id;

}
