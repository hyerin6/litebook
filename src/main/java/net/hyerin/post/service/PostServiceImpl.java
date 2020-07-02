package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.request.InsertPostDto;
import net.hyerin.post.repository.PostRepository;
import net.hyerin.post.request.ModifyPostRequest;
import net.hyerin.post.response.FeedsResponse;
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
        return postId == null ?
            this.postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId) :
            this.postRepository.findTop5ByUser_IdAndIdLessThanOrderByIdDescStartedDateDesc(userId, postId);
    }

    @Override
    public List<Post> getFriendPosts(Long postId, Long userId) {
        return postId == null ?
            this.postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId) :
            this.postRepository.findTop5ByUser_IdAndIdLessThanOrderByIdDescStartedDateDesc(userId, postId);
    }

    @Override
    public FeedsResponse getMinIdOfPosts(Long userId){
        Long minId = postRepository.findMinIdByUserId(userId);
        return FeedsResponse.builder()
            .minIdOfPosts(minId == null ? new Long(0) : minId)
            .build();
    }

    @Override
    public FeedsResponse getMinIdOfFriendPosts(Long userId) {
        Long minId = postRepository.findMinIdByFriendUserId(userId);
        return FeedsResponse.builder()
            .minIdOfPosts(minId == null ? new Long(0) : minId)
            .build();
    }

    @Override
    public List<Post> getFeeds(Long postId, Long userId) {
        return postId == null ?
            this.postRepository.findByFriendUserId(userId) :
            this.postRepository.findByIdAndFriendUserId(postId, userId);
    }

    @Override
    @Transactional
    public void modifyPost(InsertPostDto insertPostDto, Long postId){
        Post post = postRepository.findById(postId).get();
        post.setMainText(insertPostDto.getMainText());
    }

    @Override
    @Transactional
    public int deletePost(Long postId, Long userId) {
        return postRepository.deleteByIdAndUserId(postId, userId);
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findOneById(postId);
    }

}
