package com.zooplus.challenge.currencyConverter.client.user;

import com.zooplus.challenge.currencyConverter.service.user.UserCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class RegistrationController {

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    private UserCreator userCreator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationFormValidator);
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView mav = new ModelAndView("user/registration");
        mav.addObject("registrationForm", new RegistrationForm());
        return mav;
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public String submitRegistration(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/registration";
        }
        userCreator.createNewUser(registrationForm.getEmail(), registrationForm.getPassword1());
        return "user/registrationSuccessfull";
    }

}
