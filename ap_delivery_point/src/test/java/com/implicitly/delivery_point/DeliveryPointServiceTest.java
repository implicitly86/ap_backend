package com.implicitly.delivery_point;

import com.implicitly.delivery_point.persistence.DeliveryPointRepository;
import com.implicitly.delivery_point.service.DeliveryPointService;
import com.implicitly.domain.deliverypoint.DeliveryPoint;
import com.implicitly.dto.deliverypoint.DeliveryPointDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Emil Murzakaev.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.properties.hibernate.dialect: org.hibernate.dialect.H2Dialect",
        "spring.datasource.url=jdbc:h2:mem:db;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration",
        "spring.redis.port=1010",
        "spring.cloud.bus.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "spring.cloud.consul.enabled=false",
        "spring.cloud.consul.config.enabled=false"
})
public class DeliveryPointServiceTest {

    @Autowired
    private DeliveryPointRepository repository;

    @Autowired
    private DeliveryPointService service;

    //@MockBean(name = "cacheManager")
    private CacheManager cacheManager;

    @Test
    @Ignore
    public void getDeliveryPointsTest() {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setName("name");
        deliveryPoint.setAddress("address");
        deliveryPoint.setEmail("email");
        deliveryPoint.setPhone("phone");
        deliveryPoint.setPostcode("postcode");
        repository.save(deliveryPoint);

        Page<DeliveryPointDTO> deliveryPoints = service.getAllDeliveryPoints(PageRequest.of(1, 10));
    }

}
