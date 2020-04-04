package net.hyerin.user.controller;

import net.hyerin.user.dto.UserSignupDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "guest")
public class GuestController {

    @PostMapping
    @RequestMapping(value = "signup")
    public String Signup(@Valid UserSignupDto userSignupDto, BindingResult bindingResult, Model model){
        return "";
    }

    @PostMapping
    @RequestMapping(value = "signin")
    public String Signin(@Valid UserSignupDto userSigninDto, BindingResult bindingResult, Model model){
        return "";
    }

}
