package gdsc.sparkling_thon.book.service;

import gdsc.sparkling_thon.book.domain.dto.BookTradeDto;
import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.domain.entity.BookTradeEntity;
import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import gdsc.sparkling_thon.book.repository.BookRepository;
import gdsc.sparkling_thon.book.repository.BookTradeRepository;
import gdsc.sparkling_thon.user.domain.UserEntity;
import gdsc.sparkling_thon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookTradeService {

	private final BookRepository bookRepository;
	private final BookTradeRepository bookTradeRepository;
	private final UserRepository userRepository;

	// 거래 요청 생성
	@Transactional
	public BookTradeDto requestTrade(Long buyBookId, Long sellBookId, String buyerTelNum) {
		// 구매하고자 하는 책을 ID로 조회
		BookEntity buyBook = bookRepository.findById(buyBookId)
			.orElseThrow(() -> new IllegalArgumentException("BuyBook not found"));

		// 판매하고자 하는 책을 ID로 조회
		BookEntity sellBook = bookRepository.findById(sellBookId)
			.orElseThrow(() -> new IllegalArgumentException("SellBook not found"));

		// 구매자의 전화번호로 사용자 조회
		UserEntity buyer = userRepository.findByTelNum(buyerTelNum)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		// 거래 엔티티 생성 및 저장
		BookTradeEntity trade = BookTradeEntity.builder()
			.buyBook(buyBook)
			.sellBook(sellBook)
			.status(TradeStateEnum.REQUEST)
			.build();

		trade = bookTradeRepository.save(trade);

		// DTO로 변환하여 반환
		return BookTradeDto.fromEntity(trade);
	}

	// 거래 요청 수락
	@Transactional
	public void acceptTrade(Long tradeId) {
		// 거래 ID로 거래 엔티티 조회
		BookTradeEntity trade = bookTradeRepository.findById(tradeId)
			.orElseThrow(() -> new IllegalArgumentException("Trade not found"));

		// 거래 상태를 ACCEPTED로 변경
		trade.setStatus(TradeStateEnum.ACCEPTED);
		bookTradeRepository.save(trade);
	}

	// 거래 요청 거절
	@Transactional
	public void rejectTrade(Long tradeId) {
		// 거래 ID로 거래 엔티티 조회
		BookTradeEntity trade = bookTradeRepository.findById(tradeId)
			.orElseThrow(() -> new IllegalArgumentException("Trade not found"));

		// 거래 상태를 REJECTED로 변경
		trade.setStatus(TradeStateEnum.REJECTED);
		bookTradeRepository.save(trade);
	}

	// 거래 완료
	@Transactional
	public void completeTrade(Long tradeId) {
		// 거래 ID로 거래 엔티티 조회
		BookTradeEntity trade = bookTradeRepository.findById(tradeId)
			.orElseThrow(() -> new IllegalArgumentException("Trade not found"));

		// 거래 상태를 COMPLETE로 변경
		trade.setStatus(TradeStateEnum.COMPLETE);
		bookTradeRepository.save(trade);
	}
}
