package net.hyerin.post.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PostController {

    private PostService postService;

    private UserService userService;

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

        postService.deletePost(postId, user.getId());

        return "redirect:/users/profile";
    }

    @GetMapping("/timeline/feeds")
    public String getTimeline(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Long userId = user.getId();

        List<Post> posts = postService.getFeeds(null, userId);

        Long lastIdOfPosts = CollectionUtils.isEmpty(posts) ?
            null : posts.get(posts.size() - 1).getId();

        model.addAttribute("posts", posts);
        model.addAttribute("lastIdOfPosts", lastIdOfPosts);
        model.addAttribute("user", user);
        model.addAttribute("minIdOfPosts", postService.getMinIdOfFriendPosts(user.getId()).getMinIdOfPosts());

        return "/timeline/feeds";
    }

}
