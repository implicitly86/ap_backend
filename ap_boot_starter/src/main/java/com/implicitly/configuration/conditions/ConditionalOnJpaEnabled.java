/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration.conditions;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Маркерная аннотация для механизма регистрации <strong>Spring Data JPA</strong>.
 *
 * @author Emil Murzakaev.
 */
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnJpaConditionalEnabled.class)
public @interface ConditionalOnJpaEnabled {
}
