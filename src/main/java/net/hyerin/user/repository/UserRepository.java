package net.hyerin.user.repository;

import net.hyerin.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   public User findOneByEmail(String email);
   public Optional<User> findByEmail(String email);
   public User save(User user);
   public int deleteByEmail(String email);
}
