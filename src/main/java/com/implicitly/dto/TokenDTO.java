/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Данные токена.
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TokenDTO {

    /**
     * Токен.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Описание ошибки.
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * Данные ошибки.
     */
    private String error;

}
