package net.hyerin.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.hyerin.post.domain.Post;
import net.hyerin.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   public User findOneById(Long id);

   public User findOneByEmail(String email);

   public Optional<User> findByEmail(String email);

   public User save(User user);

   public int deleteByEmail(String email);

   public List<User> findByName(String name);

}
