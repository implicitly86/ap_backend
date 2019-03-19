/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.converter;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Реализация {@link AttributeConverter} для конвертации {@link LocalDateTime} в {@link Timestamp}.
 *
 * @author Emil Murzakaev.
 */
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * {@link AttributeConverter#convertToDatabaseColumn}
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute == null ? null : Timestamp.valueOf(attribute));
    }

    /**
     * {@link AttributeConverter#convertToEntityAttribute}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return (dbData == null ? null : dbData.toLocalDateTime());
    }

}
