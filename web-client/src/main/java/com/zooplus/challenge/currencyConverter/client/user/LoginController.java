package com.zooplus.challenge.currencyConverter.client.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
class LoginController {

    @RequestMapping("/login.html")
    public ModelAndView loginPage() {
        return new ModelAndView("/user/login");
    }

}
