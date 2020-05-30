package net.hyerin.post.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.post.dto.InsertPostDto;
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

    @RequestMapping(value="/posts/insertPost", method = RequestMethod.POST)
    public String insertPost(@ModelAttribute("insertPostDto") InsertPostDto insertPostDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        log.info("post mainText : " + insertPostDto.getMainText());
        postService.insertPost(insertPostDto, user);
        return "redirect:/users/profile";
    }

}
