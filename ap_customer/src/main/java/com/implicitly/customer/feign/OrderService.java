package com.implicitly.customer.feign;

import com.implicitly.dto.order.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Клиент доступа к сервису заказов.
 *
 * @author Emil Murzakaev.
 */
@FeignClient(name = "ap-order", path = "/api/v1")
public interface OrderService {

    /**
     * Получение списка {@link OrderDTO}, относящихся к определенному заказчику.
     *
     * @param customerId идентификатор заказчика.
     * @param pageable   {@link Pageable}
     */
    @GetMapping(value = "/order/customer/{customerId}")
    ResponseEntity<Page<OrderDTO>> getOrders(@PathVariable("customerId") Long customerId, Pageable pageable);

}
