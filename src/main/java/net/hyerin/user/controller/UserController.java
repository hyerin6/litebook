package net.hyerin.user.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.user.dto.UserSignupDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserController {

    @PostMapping(value = "signup")
    public String Signup(@Valid UserSignupDto userSignupDto, BindingResult bindingResult, Model model){
        return "";
    }

    @PostMapping(value = "signin")
    public String Signin(@Valid UserSignupDto userSigninDto, BindingResult bindingResult, Model model){
        return "";
    }

}
