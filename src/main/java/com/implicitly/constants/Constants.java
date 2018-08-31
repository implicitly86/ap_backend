/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.constants;

/**
 * Константы, используемые в приложении.
 *
 * @author Emil Murzakaev.
 */
public class Constants {

    public static final String MESSAGE = "message";
    public static final String BODY = "body";
    public static final String ENTITY_BASE_PACKAGE = "com.implicitly.domain";
    public static final String REPOSITORY_BASE_PACKAGE = "com.implicitly.persistence";

    public static final String CACHE_CUSTOMERS = "get-customers";
    public static final String CACHE_DELIVERY_POINTS = "get-delivery-points";
    public static final String CACHE_ORDERS = "get-orders";
    public static final String CACHE_USER_BY_ID = "get-user-by-id";
    public static final String CACHE_USER_BY_NAME = "get-user-by-name";

    private Constants() {
    }

}
