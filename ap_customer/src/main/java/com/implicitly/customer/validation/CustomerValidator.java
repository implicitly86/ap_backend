/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.customer.validation;

import com.implicitly.dto.customer.CustomerDTO;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.springframework.util.StringUtils;

/**
 * Валидация {@link CustomerDTO}
 *
 * @author Emil Murzakaev.
 */
public class CustomerValidator {

    private static final String VALID_NAME_CHARS = "[а-яА-Я ]";
    private static final String VALID_NUMBERS = "[0-9+]";

    /**
     * Валидация {@link CustomerDTO}
     *
     * @param customer {@link CustomerDTO}
     * @return результат валидации.
     */
    public Validation<Seq<String>, CustomerDTO> validateCustomer(CustomerDTO customer) {
        return Validation.combine(
                validateName(customer.getName()),
                validateFirstName(customer.getFirstName()),
                validateLastName(customer.getLastName()),
                validatePhone(customer.getPhone())
        ).ap((name, firstName, lastName, phone) -> CustomerDTO.builder()
                .id(customer.getId())
                .name(name)
                .firstName(firstName)
                .lastName(lastName)
                .address(customer.getAddress())
                .phone(phone)
                .email(customer.getEmail())
                .type(customer.getType())
                .build()
        );
    }

    /**
     * Валидация названия клиента.
     *
     * @param name название клиента.
     * @return результат валидации имени.
     */
    private Validation<String, String> validateName(String name) {
        if (StringUtils.isEmpty(name)) {
            return Validation.valid(name);
        }
        return CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "")
                .transform(seq -> seq.isEmpty() ? Validation.valid(name) : Validation.invalid("Название содержит неккоректные символы: '"
                        + seq.distinct().sorted() + "'")
                );
    }

    /**
     * Валидация имени клиента.
     *
     * @param firstName имя клиента.
     * @return результат валидации имени клиента.
     */
    private Validation<String, String> validateFirstName(String firstName) {
        if (StringUtils.isEmpty(firstName)) {
            return Validation.valid(firstName);
        }
        return CharSeq.of(firstName).replaceAll(VALID_NAME_CHARS, "")
                .transform(seq -> seq.isEmpty() ? Validation.valid(firstName) : Validation.invalid("Имя содержит неккоректные символы: '"
                        + seq.distinct().sorted() + "'")
                );
    }

    /**
     * Валидация фамилии клиента.
     *
     * @param lastName фамилия клиента.
     * @return результат валидации фамилии клиента.
     */
    private Validation<String, String> validateLastName(String lastName) {
        if (StringUtils.isEmpty(lastName)) {
            return Validation.valid(lastName);
        }
        return CharSeq.of(lastName).replaceAll(VALID_NAME_CHARS, "")
                .transform(seq -> seq.isEmpty() ? Validation.valid(lastName) : Validation.invalid("Фамилия содержит неккоректные символы: '"
                        + seq.distinct().sorted() + "'")
                );
    }

    /**
     * Валидация номера телефона клиента.
     *
     * @param phone номер телефона клиента.
     * @return результат валидации номера телефона клиента.
     */
    private Validation<String, String> validatePhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return Validation.valid(phone);
        }
        return CharSeq.of(phone).replaceAll(VALID_NUMBERS, "")
                .transform(seq -> seq.isEmpty() ? Validation.valid(phone) : Validation.invalid("Номер телефона содержит неккоректные символы: '"
                        + seq.distinct().sorted() + "'")
                );
    }

}
