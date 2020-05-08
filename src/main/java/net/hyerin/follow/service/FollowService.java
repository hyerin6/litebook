package net.hyerin.follow.service;

import net.hyerin.follow.domain.Follow;

import java.util.List;

public interface FollowService {
    public Follow follow(Long followingId);
    public List<Follow> findByFollowerId(Long followerId);
    public List<Follow> findByFollowingId(Long followingId);
}
