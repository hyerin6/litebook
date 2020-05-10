package net.hyerin.post.repository;

import net.hyerin.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

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

}
