package gdsc.sparkling_thon.book.domain.dto;

import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;
import gdsc.sparkling_thon.book.domain.enums.BookStateEnum;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookInfoDto {
	private Long id;
	private String title;
	private BookStateEnum status;
	private String author;
	private String publisher;
	private Long imageId;  // 이미지 ID 추가

	// OriginalBookEntity에서 필요한 데이터를 전달받아 초기화
	public BookInfoDto(Long id, String title, String author, String publisher) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	// OriginalBookEntity를 직접 받는 생성자
	public BookInfoDto(OriginalBookEntity originalBookEntity) {
		this.id = originalBookEntity.getId();
		this.title = originalBookEntity.getTitle();
		this.author = originalBookEntity.getAuthor();
		this.publisher = originalBookEntity.getPublisher();
	}

	// BookEntity에서 필요한 데이터를 전달받아 초기화하는 생성자
	public BookInfoDto(BookEntity bookEntity) {
		this.id = bookEntity.getId();
		this.title = bookEntity.getOriginalBook().getTitle();
		this.status = bookEntity.getStatus();
		this.author = bookEntity.getOriginalBook().getAuthor();
		this.publisher = bookEntity.getOriginalBook().getPublisher();
		this.imageId = bookEntity.getImageId();  // 이미지 ID 설정
	}

	// BookEntity 리스트를 BookInfoDto 리스트로 변환
	public static List<BookInfoDto> of(List<BookEntity> books) {
		return books.stream()
			.map(BookInfoDto::new)
			.collect(Collectors.toList());
	}

	public static List<BookInfoDto> ofOriginal(List<OriginalBookEntity> originalBooks) {
		return originalBooks.stream()
			.map(BookInfoDto::new)
			.collect(Collectors.toList());
	}
}
