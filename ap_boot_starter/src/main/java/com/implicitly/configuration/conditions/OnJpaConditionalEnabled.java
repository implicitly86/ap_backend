/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration.conditions;

import com.implicitly.constants.Constants;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ConfigurationCondition;

/**
 * Реализация {@link AllNestedConditions} для механизма регистрации Spring DATA JPA.
 *
 * @author Emil Murzakaev.
 */
public class OnJpaConditionalEnabled extends AllNestedConditions {

    public OnJpaConditionalEnabled() {
        super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = Constants.JPA_ENABLED,
            havingValue = "true"
    )
    public static class OnJPAEnabled {
        // NOP
    }
}
