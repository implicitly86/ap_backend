package com.implicitly.customer.service;

import com.implicitly.customer.feign.OrderService;
import com.implicitly.customer.persistence.CustomerRepository;
import com.implicitly.customer.service.impl.CustomerServiceImpl;
import com.implicitly.domain.customer.Customer;
import com.implicitly.dto.customer.CustomerDTO;
import com.implicitly.utils.mapper.customer.CustomerMapper;
import com.implicitly.utils.mapper.security.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование реализации {@link CustomerService}.
 *
 * @author Emil Murzakaev.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private static final long id = 1L;

    /**
     * {@link CustomerRepository}
     */
    @Mock
    private CustomerRepository repository;
    /**
     * {@link OrderService}
     */
    @Mock
    private OrderService orderService;

    /**
     * Реализация {@link CustomerService}
     */
    private CustomerServiceImpl service;

    /**
     * {@link Before}
     */
    @Before
    public void setup() {
        CustomerMapper mapper = new CustomerMapper(new UserMapper());
        service = new CustomerServiceImpl(repository, orderService, mapper);
    }

    /**
     * Тестирование метода {@link CustomerService#getAllCustomers(Pageable)},
     * при котором должны быть возвращены все клиенты.
     */
    @Test
    public void shouldGetAllCustomers() {
        Customer customer = new Customer();
        customer.setId(id);
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl(Collections.singletonList(customer)));
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerDTO> customers = service.getAllCustomers(pageable);

        verify(repository).findAll(any(Pageable.class));
        Assert.assertNotNull(customers);
        Assert.assertFalse(CollectionUtils.isEmpty(customers.getContent()));
        CustomerDTO customerDTO = customers.getContent().get(0);
        Assert.assertEquals(id, customerDTO.getId(), 0L);
    }

    /**
     * Тестирование метода {@link CustomerService#getCustomer(Long)},
     * при котором должны быть возвращен клиент с заданным id.
     */
    @Test
    public void shouldGetCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerDTO customerDTO = service.getCustomer(id);
        Assert.assertNotNull(customerDTO);
        Assert.assertEquals(id, customerDTO.getId(), 0L);
    }

}
