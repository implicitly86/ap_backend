/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.customer.persistence;

import com.implicitly.domain.customer.Customer;
import com.implicitly.persistence.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Репозиторий сущности {@link Customer}.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface CustomerRepository extends BaseRepository<Customer, Long> {

}
