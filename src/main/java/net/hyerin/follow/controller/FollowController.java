package net.hyerin.follow.controller;

import net.hyerin.follow.domain.Follow;
import net.hyerin.follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "follows")
public class FollowController {

    private FollowService followService;

    @Autowired
    public FollowController(FollowService followService){
        this.followService = followService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String follow(@PathVariable("id") Long followingId){
        followService.follow(followingId);
        return "redirect:/users/"+followingId;
    }

}

