/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.customer;

import com.implicitly.domain.IdentifiedEntity;
import com.implicitly.domain.security.User;
import com.implicitly.dto.customer.CustomerType;
import com.implicitly.utils.converter.CustomerTypeConverter;
import com.implicitly.utils.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Доменная модель сущности <strong>Клиент</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`customer`")
public class Customer implements IdentifiedEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4640186599507173911L;

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_gen")
    @SequenceGenerator(name = "customer_gen", sequenceName = "sq_customer", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Название клиента.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Имя клиента.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия клиента.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество клиента.
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Адрес клиента.
     */
    @Column(name = "address")
    private String address;

    /**
     * Телефон клиента.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Почта клиента.
     */
    @Column(name = "email")
    private String email;

    /**
     * Тип клиента.
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = CustomerTypeConverter.class)
    private CustomerType type;

    /**
     * Дата создания.
     */
    @Column(name = "createddt", nullable = false, columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    /**
     * Идентификатор пользователя, создавшего запись.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby_id", nullable = false)
    private User author;

}
