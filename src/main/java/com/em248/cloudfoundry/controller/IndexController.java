package com.em248.cloudfoundry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tian on 2017/3/21.
 */
@Controller
public class IndexController {


    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("add_service")
    public String addService() {
        return "add_service";
    }

    @GetMapping("add_plan")
    public String addPlan() {
        return "add_plan";
    }

}
