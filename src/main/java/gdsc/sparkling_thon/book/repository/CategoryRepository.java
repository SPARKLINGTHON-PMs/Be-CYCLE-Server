package gdsc.sparkling_thon.book.repository;

import gdsc.sparkling_thon.book.domain.CategoryEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
