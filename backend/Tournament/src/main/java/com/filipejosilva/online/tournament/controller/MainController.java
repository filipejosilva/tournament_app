package com.filipejosilva.online.tournament.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(method = RequestMethod.GET, path={"/", ""})
    public String getIndex(){
        return "index";
    }
}
