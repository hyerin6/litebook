package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.dto.InsertPostDto;
import net.hyerin.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    public void insertPost(InsertPostDto insertPostDto, User user);
    public List<Post> findByUserId(Long userId);
    public List<Post> findByFriendId(Long userId);
    public List<Post> getPosts(Long postId, Long userId);
    public Long getMinIdOfPosts(Long userId);

}
