/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.order;

import com.implicitly.domain.IdentifiedEntity;
import com.implicitly.domain.customer.Customer;
import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.domain.security.User;
import com.implicitly.utils.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
 * Доменная модель сущности <strong>Заказ</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order implements IdentifiedEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -9012425500859686090L;

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "sq_order", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Штрих-код заказа.
     */
    @Column(name = "barcode", nullable = false)
    private String barcode;

    /**
     * Клиент.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * Пункт приема заказа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_point", nullable = false)
    private DeliveryPoint fromPoint;

    /**
     * Пункт доставки заказа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_point", nullable = false)
    private DeliveryPoint toPoint;

    /**
     * Дата создания заказа.
     */
    @Column(name = "createddt", nullable = false, columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    /**
     * Пользователь, создавший запись.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby_id", nullable = false)
    private User author;

    /**
     * Дата изменения записи.
     */
    @Column(name = "modifieddt", nullable = false, columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedDate;

    /**
     * Пользователь, изменивший запись.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedby_id", nullable = false)
    private User modifiedBy;

}
