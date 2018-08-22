/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.persistence.order;

import com.implicitly.domain.order.Order;
import com.implicitly.persistence.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Репозиторий сущности {@link Order}.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface OrderRepository extends BaseRepository<Order, Long> {

}
