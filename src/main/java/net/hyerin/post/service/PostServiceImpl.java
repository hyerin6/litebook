package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.dto.InsertPostDto;
import net.hyerin.post.repository.PostRepository;
import net.hyerin.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public void insertPost(InsertPostDto insertPostDto, User user) {
        // starteDate 저장
        insertPostDto.setStartedDate(new Date());

        // Insert post
        Post post = insertPostDto.toEntity(user);
        postRepository.save(post);
    }

    @Override
    public List<Post> findByUserId(Long userId){
        return postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId);
    }

    @Override
    public List<Post> findByFriendId(Long userId){
        return postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId);
    }

    @Override
    public List<Post> getPosts(Long postId, Long userId) {
        final List<Post> posts = get(postId, userId);
        return posts;
    }

    private List<Post> get(Long id, Long userId) {
        return id == null ?
                this.postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId) :
                this.postRepository.findTop5ByUser_IdAndIdLessThanOrderByIdDescStartedDateDesc(userId, id);
    }

    @Override
    public Long getMinIdOfPosts(Long userId){
        return postRepository.findMinIdByUserId(userId);
    }

}
