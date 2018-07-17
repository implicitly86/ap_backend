/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.gson;

import com.google.gson.Gson;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, указывающая {@link Gson} игнорировать поле.
 *
 * @author Emil Murzakaev.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GsonIgnore {
}
