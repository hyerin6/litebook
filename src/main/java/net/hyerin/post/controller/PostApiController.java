package net.hyerin.post.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import net.hyerin.post.domain.Post;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PostApiController {

    private PostService postService;

    private UserService userService;

    public PostApiController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value="/myPosts", method = RequestMethod.POST)
    public PostsResponse getMyPosts(@RequestBody GetPostsRequest getPostsRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), user.getId());
        Long lastIdOfPosts = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        PostsResponse result = PostsResponse.builder()
                .posts(posts)
                .lastIdOfPosts(lastIdOfPosts)
                .build();
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/friendPosts", method = RequestMethod.POST)
    public PostsResponse getFriendPosts(@RequestBody GetPostsRequest getPostsRequest, Long userId) {
        List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), userId);
        Long lastIdOfPosts = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        PostsResponse result = PostsResponse.builder()
                .posts(posts)
                .lastIdOfPosts(lastIdOfPosts)
                .build();
        return result;
    }

    @Getter
    static class GetPostsRequest{
        private Long lastIdOfPosts;
    }

    @Getter
    @Builder
    static class PostsResponse{
        private List<Post> posts;
        private Long lastIdOfPosts;
    }

}
