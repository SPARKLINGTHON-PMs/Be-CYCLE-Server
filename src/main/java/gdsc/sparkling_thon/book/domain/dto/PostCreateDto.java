package gdsc.sparkling_thon.book.domain.dto;

import gdsc.sparkling_thon.book.domain.enums.BookStateEnum;
import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCreateDto {
	private Long originalBookId; // 사용자가 선택한 OriginalBook의 ID
	private List<Long> categoryIds; // 선택된 카테고리 ID 리스트
	private BookStateEnum status; // 책 상태
	private TradeStateEnum tradeState; // 거래 방식
}