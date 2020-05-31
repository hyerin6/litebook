package net.hyerin.post.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class PostController {

    private PostService postService;

    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value="/posts")
    public String insertPost(@ModelAttribute("insertPostDto") InsertPostDto insertPostDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        postService.insertPost(insertPostDto, user);
        return "redirect:/users/profile";
    }

    @GetMapping("/posts/{id}")
    public String deletePost(@PathVariable("id") Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        // 삭제 기능 추가

        return "redirect:/users/profile";
    }

}
