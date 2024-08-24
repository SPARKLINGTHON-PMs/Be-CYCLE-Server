package gdsc.sparkling_thon.user.repository;

import gdsc.sparkling_thon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelNum(String loginId);
}
