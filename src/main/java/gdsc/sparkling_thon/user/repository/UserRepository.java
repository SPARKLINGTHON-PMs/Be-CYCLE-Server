package gdsc.sparkling_thon.user.repository;

import gdsc.sparkling_thon.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelNum(String loginId);
}
