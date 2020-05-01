package net.hyerin.user.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.security.ValidationFailedException;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserController {

    private EmailService emailService;

    private UserService userService;

    @Autowired
    public UserController(UserService userService, EmailService emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping(value="signup", method = RequestMethod.GET)
    public String signup(Model model, UserSignupDto userSignupDto){
        return "users/signup";
    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute("userSignupDto") UserSignupDto userSignupDto,
                         BindingResult bindingResult, Model model){
        if (userService.hasErrors(userSignupDto, bindingResult))
            return "users/signup";
        try{
            userService.signup(userSignupDto);
        } catch (Exception e){
            log.error(e.toString());
            return "users/signupFail";
        }
        return "users/signupSuccess";
    }

    @RequestMapping(value = "signupSuccess", method = RequestMethod.GET)
    public String signupSuccess() {
        return "users/signupSuccess";
    }

    @RequestMapping(value = "signupFail", method = RequestMethod.GET)
    public String signupFail() {
        return "users/signupFail";
    }

    @RequestMapping(value = "email/verify", method = RequestMethod.GET)
    public String verifyCode(@RequestParam("email") String email, @RequestParam("code") String code){
        if(emailService.isMatchVerifyCode(email, code)){
            userService.updateUserType(email);
            return "/auth/success";
        }
        return "/auth/fail";
    }

    @RequestMapping(value = "auth/success", method = RequestMethod.GET)
    public String authSuccess(){
        return "users/auth/success";
    }

    @RequestMapping(value = "auth/fail", method = RequestMethod.GET)
    public String authFail(){
        return "users/auth/fail";
    }

    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public String signin(Model model, UserSigninDto userSigninDto){
        return "users/signin";
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public String signin(@Valid @ModelAttribute("userSigninDto") UserSigninDto userSigninDto,
                         BindingResult bindingResult, Model model) {
        return "users/profile";
    }

    @RequestMapping(value = "signin_processing", method = RequestMethod.POST)
    public String signinPrceocessing(@Valid @ModelAttribute("userSigninDto") UserSigninDto userSigninDto,
                         BindingResult bindingResult, Model model) {
        return "users/profile";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest request){
        HttpSession session = request.getSession();
        return "users/profile";
    }

}
