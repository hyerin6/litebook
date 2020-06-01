package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.repository.PostRepository;
import net.hyerin.post.request.ModifyPostRequest;
import net.hyerin.user.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Long minId = postRepository.findMinIdByUserId(userId);
        return minId == null ? new Long(0) : minId;
    }

    @Override
    @Transactional
    public void modifyPost(InsertPostDto insertPostDto, Long postId){
        Post post = postRepository.findById(postId).get();
        post.setMainText(insertPostDto.getMainText());
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId) {
        postRepository.deleteByIdAndUserId(postId, userId);
    }

}
