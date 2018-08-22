/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.service;

import com.implicitly.dto.customer.CustomerDTO;
import java.util.List;

/**
 * Сервис работы с сущностью {@link CustomerDTO}
 *
 * @author Emil Murzakaev.
 */
public interface CustomerService {

    /**
     * Получение всех сущностей {@link CustomerDTO}.
     *
     * @return список {@link CustomerDTO}
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Получение {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @return {@link CustomerDTO}.
     */
    CustomerDTO getCustomer(Long id);

    /**
     * Сохранение {@link CustomerDTO}
     *
     * @param customer {@link CustomerDTO}.
     */
    CustomerDTO saveCustomer(CustomerDTO customer);

    /**
     * Редактирование {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     * @param customer {@link CustomerDTO}.
     */
    void updateCustomer(Long id, CustomerDTO customer);

    /**
     * Удаление {@link CustomerDTO} по уникальному идентификатору.
     *
     * @param id уникальный идентификатор.
     */
    void deleteCustomer(Long id);

}
