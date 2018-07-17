/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Реализация {@link ExclusionStrategy}, позволяющая игнорировать поля с помощью {@link GsonIgnore}.
 *
 * @author Emil Murzakaev.
 */
public class GsonIgnoreStrategy implements ExclusionStrategy {

    /**
     * {@link ExclusionStrategy#shouldSkipField}
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(GsonIgnore.class) != null;
    }

    /**
     * {@link ExclusionStrategy#shouldSkipClass}
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
