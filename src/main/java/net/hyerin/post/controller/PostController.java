package net.hyerin.post.controller;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.post.dto.InsertPostDto;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.security.CustomUserDetailsService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value="/posts/insertPost", method = RequestMethod.POST)
    public String insertPost(@ModelAttribute("insertPostDto") InsertPostDto insertPostDto) throws ParseException {
        postService.insertPost(insertPostDto);
        return "redirect:/users/profile";
    }


}
