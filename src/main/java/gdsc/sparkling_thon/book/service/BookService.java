package gdsc.sparkling_thon.book.service;

import gdsc.sparkling_thon.book.domain.dto.BookInfoDto;
import gdsc.sparkling_thon.book.domain.dto.PostCreateDto;
import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;
import gdsc.sparkling_thon.book.domain.enums.SearchCategoryEnum;
import gdsc.sparkling_thon.book.repository.BookRepository;
import gdsc.sparkling_thon.book.repository.CategoryRepository;
import gdsc.sparkling_thon.book.repository.DefaultBookRepository;
import gdsc.sparkling_thon.book.repository.OriginalBookRepository;
import gdsc.sparkling_thon.user.domain.UserEntity;
import gdsc.sparkling_thon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final DefaultBookRepository defaultBookRepository;
	private final UserRepository userRepository;
	private final OriginalBookRepository originalBookRepository;
	private final CategoryRepository categoryRepository;

	// OCR 결과로 책 검색
	public List<BookInfoDto> searchBooksByOcrResult(String ocrText) {
		List<OriginalBookEntity> originalBooks = originalBookRepository.findByTitleContaining(ocrText);

		// OriginalBookEntity를 BookInfoDto로 변환
		return originalBooks.stream()
			.map(BookInfoDto::new) // BookInfoDto 생성자로 변환
			.collect(Collectors.toList());
	}

	// 포스트 생성
	public BookEntity createBookPost(PostCreateDto bookCreateDto, String telNum) {
		UserEntity user = userRepository.findByTelNum(telNum)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		OriginalBookEntity originalBook = originalBookRepository.findById(bookCreateDto.getOriginalBookId())
			.orElseThrow(() -> new IllegalArgumentException("OriginalBook not found"));

		// Set으로 변환하여 CategoryEntity 리스트를 가져옴
		Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(bookCreateDto.getCategoryIds()));

		BookEntity book = BookEntity.builder()
			.user(user)
			.originalBook(originalBook)
			.image(bookCreateDto.getImage())
			.status(bookCreateDto.getStatus())
			.tradeState(bookCreateDto.getTradeState()) // 거래 방식 설정
			.categories(categories) // 카테고리 설정
			.build();

		return bookRepository.save(book);
	}

    public List<BookInfoDto> getBookList() {
return null;
    }

	public List<String> getCategoriesName(String userId, Set<String> options) {
		return null;
	}

	public List<BookEntity> getBooks(String userId, Set<SearchCategoryEnum> options) {
		return defaultBookRepository.getBooks(userId, options. stream().collect(Collectors.toSet()), 0, 0);

	}
}
