package gdsc.sparkling_thon.book.domain.enums;

public enum TradeStateEnum {
    // REJECTED: 거래 요청이 거절된 상태
    // ACCEPTED: 거래 요청은 수락되었지만 거래에 필요한 정보가 입력되지 않은 상태
    // COMPLETE: 책을 택배로 보내거나, 직거래가 완료된 상태
    REQUEST, ACCEPTED, REJECTED, COMPLETE
}
