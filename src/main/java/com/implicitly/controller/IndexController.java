/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Корневой обработчик запросов.
 *
 * @author Emil Murzakaev.
 */
@Controller
public class IndexController {

    /**
     * Перенаправляет пользователя на страницу с докуентацией API.
     */
    @GetMapping(value = "/")
    public View index() {
        return new RedirectView("swagger-ui.html");
    }

}
