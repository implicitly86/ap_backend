/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.persistence;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Базовый репозиторий для JPA сущностей.
 *
 * @author Emil Murzakaev.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
