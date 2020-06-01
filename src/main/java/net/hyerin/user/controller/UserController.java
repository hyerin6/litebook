package net.hyerin.user.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import net.hyerin.follow.service.FollowService;
import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSigninDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserController {

    private EmailService emailService;

    private UserService userService;

    private PostService postService;

    private FollowService followService;

    @Autowired
    public UserController(UserService userService, EmailService emailService,
                          PostService postService, FollowService followService){
        this.userService = userService;
        this.emailService = emailService;
        this.postService = postService;
        this.followService = followService;
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
    public String signin(Model model, @ModelAttribute("userSigninDto")UserSigninDto userSigninDto){
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

    // 나의 프로필 : user.posts 와 POSTS 메뉴에서 글쓰기 기능을 위해 insertPostDto 가 필요하다.
    @RequestMapping(value = "profile", method = RequestMethod.GET)
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
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String profile(@PathVariable("id") Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.findByEmail(auth.getName());

        List<Post> posts = postService.getPosts(null, id);

        Long lastIdOfPosts = posts.isEmpty() ?
            new Long(0) : posts.get(posts.size() - 1).getId();

        model.addAttribute("posts", posts);
        model.addAttribute("lastIdOfPosts", lastIdOfPosts);
        model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(id));
        model.addAttribute("loginUser", userService.findByEmail(loginUser.getEmail()));
        model.addAttribute("user", userService.findById(id));

        return "friends/profile";
    }

}
