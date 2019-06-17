/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.domain.security;

import com.implicitly.domain.IdentifiedEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Доменная модель сущности <strong>Роль</strong>.
 *
 * @author Emil Murzakaev.
 */
@Data
@Entity
@Table(name = "`role`")
public class Role implements IdentifiedEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5076844046294844849L;

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name = "role_gen", sequenceName = "sq_role", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Название роли.
     */
    @Column(name = "name", nullable = false)
    private String name;

}
