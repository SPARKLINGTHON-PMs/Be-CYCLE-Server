package gdsc.sparkling_thon.book.repository;



import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
