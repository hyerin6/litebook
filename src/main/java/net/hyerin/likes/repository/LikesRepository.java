package net.hyerin.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.hyerin.likes.domain.Likes;
import net.hyerin.post.domain.Post;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

	@Query("SELECT l FROM Likes l WHERE l.user.id = :userId AND l.post.id = :postId")
	public Likes findByUserIdAndPostId(Long userId, Long postId);

	@Query("SELECT COUNT(l) FROM Likes l WHERE l.checkLike = true AND l.post.id = :postId")
	public int findByLikes(Long postId);

}
