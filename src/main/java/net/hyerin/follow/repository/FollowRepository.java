package net.hyerin.follow.repository;

import net.hyerin.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    public Follow save(Follow follow);
    public List<Follow> findByFollowerId(Long followerId);
    public List<Follow> findByFollowingId(Long followingId);

}
