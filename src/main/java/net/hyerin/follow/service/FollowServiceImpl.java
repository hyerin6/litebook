package net.hyerin.follow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hyerin.follow.domain.Follow;
import net.hyerin.follow.repository.FollowRepository;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;
    private UserService userService;

    public FollowServiceImpl(FollowRepository followRepository, UserService userService){
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public Follow follow(User follower, Long followingId){
        // 이미 팔로우한 친구는 insert 되지 않는다.
        if(followRepository.findByFollowerIdAndFollowingId(follower.getId(), followingId) != null)
            return null;

        Follow follow = Follow.builder()
                .follower(follower)
                .following(userService.findById(followingId))
                .build();

        return followRepository.save(follow);
    }

    @Override
    public List<Follow> findByFollowerId(Long followerId){
        return followRepository.findByFollowerId(followerId);
    }

    @Override
    public List<Follow> findByFollowingId(Long followingId){
        return followRepository.findByFollowingId(followingId);
    }

    @Override
    @Transactional
    public void deleteByFollowerId(Long followerId, Long followingId){
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

}
