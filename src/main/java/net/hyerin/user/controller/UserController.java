package net.hyerin.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.hyerin.email.service.EmailService;
import net.hyerin.follow.service.FollowService;
import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserModifyDto;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserController {

    private EmailService emailService;

    private UserService userService;

    private PostService postService;

    public UserController(UserService userService, EmailService emailService,
                          PostService postService) {
        this.userService = userService;
        this.emailService = emailService;
        this.postService = postService;
    }

    @GetMapping(value="signup")
    public String signup(Model model, UserSignupDto userSignupDto){
        return "users/signup";
    }

    @PostMapping(value="signup")
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

    @GetMapping(value = "signupSuccess")
    public String signupSuccess() {
        return "users/signupSuccess";
    }

    @GetMapping(value = "signupFail")
    public String signupFail() {
        return "users/signupFail";
    }

    @GetMapping(value = "email/verify")
    public String verifyCode(@RequestParam("email") String email, @RequestParam("code") String code){
        if(emailService.isMatchVerifyCode(email, code)){
            userService.updateUserType(email);
            return "/auth/success";
        }
        return "/auth/fail";
    }

    @GetMapping(value = "auth/success")
    public String authSuccess(){
        return "users/auth/success";
    }

    @GetMapping(value = "auth/fail")
    public String authFail(){
        return "users/auth/fail";
    }

    @GetMapping(value = "signin")
    public String signin(Model model, @ModelAttribute("userSigninDto")UserSigninDto userSigninDto){
        return "users/signin";
    }

    @PostMapping(value = "signin")
    public String signin(@Valid @ModelAttribute("userSigninDto") UserSigninDto userSigninDto,
                         BindingResult bindingResult, Model model) {
        return "users/profile";
    }

    @PostMapping(value = "signin_processing")
    public String signinPrceocessing(@Valid @ModelAttribute("userSigninDto") UserSigninDto userSigninDto,
                                     BindingResult bindingResult, Model model) {
        return "users/profile";
    }

    // 나의 프로필 : user.posts 와 POSTS 메뉴에서 글쓰기 기능을 위해 insertPostDto 가 필요하다.
    @GetMapping(value = "profile")
    public String profile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<Post> posts = postService.getPosts(null, user.getId());

        Long lastIdOfPosts = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        model.addAttribute("insertPostDto", new InsertPostDto());
        model.addAttribute("posts", posts);
        model.addAttribute("lastIdOfPosts", lastIdOfPosts);
        model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(user.getId()));
        model.addAttribute("user", userService.findByEmail(user.getEmail()));
        return "users/profile";
    }

    // 다른 사용자 프로필 : id 에 해당하는 user, user.posts 가 필요하다.
    @GetMapping(value = "{id}")
    public String profile(@PathVariable("id") Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.findByEmail(auth.getName());

        model.addAttribute("loginUser", userService.findByEmail(loginUser.getEmail()));
        model.addAttribute("user", userService.findById(id));

        List<Post> posts = postService.getPosts(null, id);

        if(CollectionUtils.isEmpty(posts)) {
            return "friends/emptyProfile";
        }

        Long lastIdOfPosts = posts.isEmpty() ?
            new Long(0) : posts.get(posts.size() - 1).getId();

        model.addAttribute("posts", posts);
        model.addAttribute("lastIdOfPosts", lastIdOfPosts);
        model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(id));

        return "friends/profile";
    }

    @GetMapping(value="modify")
    public String modifyProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.findByEmail(auth.getName());

        model.addAttribute("user", userService.findByEmail(loginUser.getEmail()));
        model.addAttribute("userModifyDto", new UserModifyDto());

        return "users/settings";
    }

    @PostMapping(value="modify")
    public String modifyProfile(UserModifyDto userModifyDto, Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.findByEmail(auth.getName());

        userService.modifyProfile(userModifyDto, loginUser.getId());

        return "redirect:/users/logout";
    }

}
