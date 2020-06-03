package net.hyerin.post.repository;

import net.hyerin.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public Post findOneById(Long id);

//    //최초로 조회할 때
//    @Query(nativeQuery = true,
//            value = "SELECT * FROM Post WHERE user_id = :userId " +
//                    "ORDER BY started_date DESC LIMIT 5")
    public List<Post> findTop5ByUser_IdOrderByStartedDateDesc(Long userId);

//    //최초가 아닌 경우
//    @Query(nativeQuery = true,
//            value = "SELECT * FROM Post " +
//            "WHERE user_id = :userId AND id < :id " +
//            "ORDER BY id DESC, started_date DESC LIMIT 10")
    public List<Post> findTop5ByUser_IdAndIdLessThanOrderByIdDescStartedDateDesc(Long userId, Long id);

    @Query(nativeQuery = true,
            value = "SELECT MIN(id) FROM Post WHERE user_id = :userId")
    public Long findMinIdByUserId(Long userId);

    @Query(nativeQuery = true,
        value = "SELECT p.id, mainText, started_date, user_id FROM Post p WHERE user_id "
            + "IN (SELECT f.following_id FROM Follow f WHERE f.follower_id = :userId) "
            + "OR p.user_id = :userId "
            + "ORDER BY p.id DESC LIMIT 5")
    public List<Post> findByFriendUserId(Long userId);

    @Query(nativeQuery = true,
        value = "SELECT MIN(p.id) FROM Post p "
            + "WHERE p.user_id IN (SELECT f.following_id FROM Follow f WHERE f.follower_id = :userId) "
            + "OR p.user_id = :userId")
    public Long findMinIdByFriendUserId(Long userId);

    @Query(nativeQuery = true,
        value = "SELECT p.id, mainText, started_date, user_id FROM Post p "
            + "WHERE (p.user_id IN (SELECT f.following_id FROM Follow f WHERE f.follower_id = :userId) "
            + "OR p.user_id = :userId) "
            + "AND p.id < :postId "
            + "ORDER BY p.id DESC LIMIT 5")
    public List<Post> findByIdAndFriendUserId(Long postId, Long userId);

    public Long deleteByIdAndUserId(Long id, Long userId);

}
