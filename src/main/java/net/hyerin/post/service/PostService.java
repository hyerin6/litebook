package net.hyerin.post.service;

import java.util.List;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.response.FeedsResponse;
import net.hyerin.user.domain.User;

public interface PostService {
    public void insertPost(InsertPostDto insertPostDto, User user);
    public List<Post> getPosts(Long postId, Long userId);
    public List<Post> getFriendPosts(Long id, Long userId);
    public FeedsResponse getMinIdOfPosts(Long userId);
    public FeedsResponse getMinIdOfFriendPosts(Long userId);
    public void modifyPost(InsertPostDto insertPostDto, Long postId);
    public void deletePost(Long postId, Long userId);
    public List<Post> getFeeds(Long postId, Long userId);
    public Post getPost(Long postId);
}
