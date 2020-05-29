package net.hyerin.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.hyerin.follow.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    public Follow save(Follow follow);

    public List<Follow> findByFollowerId(Long followerId);

    public List<Follow> findByFollowingId(Long followingId);

    public Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    public void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
