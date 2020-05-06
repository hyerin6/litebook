package net.hyerin.post.repository;

import net.hyerin.post.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT post from Post post where post.user.id = :userId ORDER BY post.startedDate DESC")
    List<Post> findByUserId(Long userId);

}
