package gdsc.sparkling_thon.book.domain.dto;

import gdsc.sparkling_thon.book.domain.entity.BookTradeEntity;
import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookTradeDto {
	private Long id;
	private Long buyBookId;
	private Long sellBookId;
	private TradeStateEnum status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// 생성자를 통해 BookTradeEntity로부터 값을 받아 DTO로 변환
	public BookTradeDto(Long id, Long buyBookId, Long sellBookId, TradeStateEnum status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.buyBookId = buyBookId;
		this.sellBookId = sellBookId;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// BookTradeEntity를 받아서 DTO로 변환하는 팩토리 메소드
	public static BookTradeDto fromEntity(BookTradeEntity entity) {
		return new BookTradeDto(
			entity.getId(),
			entity.getBuyBook().getId(),
			entity.getSellBook().getId(),
			entity.getStatus(),
			entity.getCreatedAt(),
			entity.getUpdatedAt()
		);
	}
}
