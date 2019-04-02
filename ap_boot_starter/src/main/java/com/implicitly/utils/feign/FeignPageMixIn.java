/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.feign;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;

/**
 * Json MixIn для включения поддержки {@link Page} в вызовах {@link FeignClient}.
 *
 * @author Emil Murzakaev.
 */
@JsonDeserialize(as = FeignPageImpl.class)
public interface FeignPageMixIn {

}
