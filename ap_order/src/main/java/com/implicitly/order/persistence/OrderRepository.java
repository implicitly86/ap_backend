/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.order.persistence;

import com.implicitly.domain.customer.Customer;
import com.implicitly.domain.order.Order;
import com.implicitly.persistence.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Репозиторий сущности {@link Order}.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface OrderRepository extends BaseRepository<Order, Long> {

    /**
     * Получение {@link Order}, принадлежащих {@link Customer}.
     *
     * @param customer {@link Customer}.
     * @param pageable {@link Pageable}.
     * @return {@link Page<Order>}.
     */
    Page<Order> findAllByCustomer(Customer customer, Pageable pageable);

}
