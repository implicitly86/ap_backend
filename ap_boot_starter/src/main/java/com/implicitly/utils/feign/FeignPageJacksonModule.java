/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.feign;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;

/**
 * Jackson модуль для поддержки {@link Page} в {@link FeignClient}.
 *
 * @author Emil Murzakaev.
 */
public class FeignPageJacksonModule extends SimpleModule {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Page.class, FeignPageMixIn.class);
    }

}
