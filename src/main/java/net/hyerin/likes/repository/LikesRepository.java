package net.hyerin.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.hyerin.likes.domain.Likes;
import net.hyerin.post.domain.Post;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

	public Likes findByUser_IdAndPost_Id(Long userId, Long postId);

}
