package gdsc.sparkling_thon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
