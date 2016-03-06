package com.zooplus.challenge.currencyConverter.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:currency/main.html";
    }

}
