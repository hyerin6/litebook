package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.request.ModifyPostRequest;
import net.hyerin.user.domain.User;

import java.util.List;

public interface PostService {

    public void insertPost(InsertPostDto insertPostDto, User user);
    public List<Post> getPosts(Long postId, Long userId);
    public Long getMinIdOfPosts(Long userId);
    public void modifyPost(InsertPostDto insertPostDto, Long postId);
    public void deletePost(Long postId, Long userId);

}
