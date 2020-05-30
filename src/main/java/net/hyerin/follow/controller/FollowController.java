package net.hyerin.follow.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.hyerin.follow.domain.Follow;
import net.hyerin.follow.service.FollowService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FollowController {

    private FollowService followService;

    private UserService userService;

    public FollowController(FollowService followService, UserService userService){
        this.followService = followService;
        this.userService = userService;
    }

    @GetMapping(value = "/follows/{id}")
    public String follow(@PathVariable("id") Long followingId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User follower = userService.findByEmail(email);

        followService.follow(follower, followingId);
        return "redirect:/followings";
    }

    @GetMapping(value = "/follows/delete/{id}")
    public String unFollow(@PathVariable("id") Long followingId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        followService.deleteByFollowerId(user.getId(), followingId);
        return "redirect:/followings";
    }

    @GetMapping(value = "/followers")
    public String followers(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));

        List<Follow> followings = followService.findByFollowingId(user.getId());

        if(CollectionUtils.isEmpty(followings)) {
            return "/follows/followersEmpty";
        }

        model.addAttribute("followings", followings);
        return "/follows/followers";
    }

    @GetMapping(value="/followings")
    public String followings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));

        List<Follow> followers = followService.findByFollowerId(user.getId());

        if(CollectionUtils.isEmpty(followers)) {
            return "/follows/followingsEmpty";
        }

        model.addAttribute("followers", followers);
        return "/follows/followings";
    }

}

