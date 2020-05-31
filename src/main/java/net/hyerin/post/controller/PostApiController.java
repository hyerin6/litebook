package net.hyerin.post.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.GetPostsRequest;
import net.hyerin.post.response.PostsResponse;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PostApiController {

    private PostService postService;

    private UserService userService;

    public PostApiController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value="/myPosts")
    public @ResponseBody PostsResponse getMyPosts(@RequestBody GetPostsRequest getPostsRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), user.getId());
        Long lastIdOfPosts = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        return PostsResponse.builder()
                .posts(posts)
                .lastIdOfPosts(lastIdOfPosts)
                .build();
    }

    @PostMapping(value="/{id}/posts")
    public  @ResponseBody PostsResponse getFriendPosts(@RequestBody GetPostsRequest getPostsRequest, @PathVariable("id") Long userId) {
        List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), userId);
        Long lastIdOfPosts = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        return PostsResponse.builder()
                .posts(posts)
                .lastIdOfPosts(lastIdOfPosts)
                .build();
    }

}
