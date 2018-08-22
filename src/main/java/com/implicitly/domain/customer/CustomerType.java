/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип {@link Customer}.
 *
 * @author Emil Murzakaev.
 */
@Getter
@AllArgsConstructor
public enum CustomerType {

    /**
     * Физическое лицо.
     */
    NATURAL_PERSON(0),
    /**
     * Юридическое лицо.
     */
    LEGAL_PERSON(1);

    /**
     * Тип.
     */
    private Integer value;

}
