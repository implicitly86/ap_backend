/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.delivery_point.persistence;

import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.persistence.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Репозиторий сущности {@link DeliveryPoint}.
 *
 * @author Emil Murzakaev.
 */
@Component
public interface DeliveryPointRepository extends BaseRepository<DeliveryPoint, Long> {

}
