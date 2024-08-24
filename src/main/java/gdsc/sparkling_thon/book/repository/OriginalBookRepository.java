package gdsc.sparkling_thon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;

public interface OriginalBookRepository extends JpaRepository<OriginalBookEntity, Long> {
	List<OriginalBookEntity> findByTitleContaining(String title);
}
