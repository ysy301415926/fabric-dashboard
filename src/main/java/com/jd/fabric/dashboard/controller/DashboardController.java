package com.jd.fabric.dashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhanglei35 on 2018/5/18.
 */
@RestController
public class DashboardController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
