/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.deliverypoint;

import com.implicitly.domain.IdentifiedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Доменная модель сущности <strong>Пункт отправки/доставки</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`deliverypoint`")
public class DeliveryPoint implements IdentifiedEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1140311153753789373L;

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverypoint_gen")
    @SequenceGenerator(name = "deliverypoint_gen", sequenceName = "sq_deliverypoint", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Название точки доставки.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Адрес точки доставки.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * Почтовый индекс точки доставки.
     */
    @Column(name = "postcode", nullable = false)
    private String postcode;

    /**
     * Телефон точки доставки.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Почта точки доставки.
     */
    @Column(name = "email")
    private String email;

}
