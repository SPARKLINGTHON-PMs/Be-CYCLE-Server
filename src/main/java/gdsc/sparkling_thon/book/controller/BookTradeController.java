package gdsc.sparkling_thon.book.controller;

import gdsc.sparkling_thon.book.domain.dto.BookTradeDto;
import gdsc.sparkling_thon.book.service.BookTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-trade")
@RequiredArgsConstructor
public class BookTradeController {

	private final BookTradeService bookTradeService;

	// 거래 요청 생성
	@PostMapping("/request")
	public ResponseEntity<BookTradeDto> requestTrade(@RequestParam Long buyBookId,
		@RequestParam Long sellBookId,
		@RequestParam String buyerTelNum) {
		BookTradeDto tradeDto = bookTradeService.requestTrade(buyBookId, sellBookId, buyerTelNum);
		return ResponseEntity.ok(tradeDto);
	}

	// 거래 요청 수락
	@PostMapping("/accept")
	public ResponseEntity<String> acceptTrade(@RequestParam Long tradeId) {
		bookTradeService.acceptTrade(tradeId);
		return ResponseEntity.ok("Trade request accepted.");
	}

	// 거래 요청 거절
	@PostMapping("/reject")
	public ResponseEntity<String> rejectTrade(@RequestParam Long tradeId) {
		bookTradeService.rejectTrade(tradeId);
		return ResponseEntity.ok("Trade request rejected.");
	}

	// 거래 완료
	@PostMapping("/complete")
	public ResponseEntity<String> completeTrade(@RequestParam Long tradeId) {
		bookTradeService.completeTrade(tradeId);
		return ResponseEntity.ok("Trade completed.");
	}
}
