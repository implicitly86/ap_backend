/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.converter;

import com.implicitly.dto.customer.CustomerType;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

/**
 * Реализация {@link AttributeConverter} для конвертации числового значения в {@link CustomerType}.
 *
 * @author Emil Murzakaev.
 */
public class CustomerTypeConverter implements AttributeConverter<CustomerType, Integer> {

    /**
     * {@link AttributeConverter#convertToDatabaseColumn}
     */
    @Override
    public Integer convertToDatabaseColumn(CustomerType attribute) {
        return attribute.getValue();
    }

    /**
     * {@link AttributeConverter#convertToEntityAttribute}
     */
    @Override
    public CustomerType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.stream(CustomerType.values())
                .filter(it -> it.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(String.format("There is no enum for value [%s]", dbData))
                );
    }

}
