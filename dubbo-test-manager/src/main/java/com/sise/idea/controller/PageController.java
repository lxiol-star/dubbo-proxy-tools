package com.sise.idea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author idea
 * @data 2019/4/8
 */
@Controller
@RequestMapping(value = "/page")
public class PageController {

    @GetMapping(value = "/index")
    public String index(){
        return "/index";
    }
}
