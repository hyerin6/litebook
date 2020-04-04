package net.hyerin.user.repository;

import net.hyerin.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   public User findOneByEmail(String email);

}
