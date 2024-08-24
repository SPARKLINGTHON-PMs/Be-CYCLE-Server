package gdsc.sparkling_thon.user.repository;

import gdsc.sparkling_thon.user.domain.UserCategoryEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategoryEntity, Long> {
}
