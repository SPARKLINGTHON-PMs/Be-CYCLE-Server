package gdsc.sparkling_thon.book.repository;

import gdsc.sparkling_thon.book.domain.entity.BookTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTradeRepository extends JpaRepository<BookTradeEntity, Long> {
}
