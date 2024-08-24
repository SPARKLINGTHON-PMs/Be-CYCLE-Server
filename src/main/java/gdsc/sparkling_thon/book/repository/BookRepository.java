package gdsc.sparkling_thon.book.repository;

import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
	// 특정 유저가 등록한 책을 조회하는 메서드
	List<BookEntity> findByUser_Id(Long userId);

	// 책 상태로 조회하는 메서드
	List<BookEntity> findByStatus(String status);
}
