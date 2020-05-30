package net.hyerin.follow.service;

import java.util.List;

import net.hyerin.follow.domain.Follow;
import net.hyerin.user.domain.User;

public interface FollowService {
    public Follow follow(User follower, Long followingId);
    public List<Follow> findByFollowerId(Long followerId);
    public List<Follow> findByFollowingId(Long followingId);
    public void deleteByFollowerId(Long followerId, Long followingId);
}
