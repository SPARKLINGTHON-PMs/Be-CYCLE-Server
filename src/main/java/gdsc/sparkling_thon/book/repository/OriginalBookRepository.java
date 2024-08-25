package gdsc.sparkling_thon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;

public interface OriginalBookRepository extends JpaRepository<OriginalBookEntity, Long> {

	// 책 제목으로 검색
	List<OriginalBookEntity> findByTitleContainingIgnoreCase(String title);

	// 책 저자로 검색
	List<OriginalBookEntity> findByAuthorContainingIgnoreCase(String author);}
