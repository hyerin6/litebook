package net.hyerin.follow.service;

import net.hyerin.follow.domain.Follow;
import net.hyerin.follow.repository.FollowRepository;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;
    private UserService userService;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, UserService userService){
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public Follow follow(Long followingId){
        // 팔로우하려는 사용자
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User follower = userService.findByEmail(email);

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

}
