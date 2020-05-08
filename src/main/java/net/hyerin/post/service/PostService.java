package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.dto.InsertPostDto;
import net.hyerin.user.domain.User;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.List;

public interface PostService {

    public void insertPost(InsertPostDto insertPostDto, User user) throws ParseException;
    public List<Post> findByUserId(Long userId);
    public List<Post> findByFriendId(Long userId);

}
