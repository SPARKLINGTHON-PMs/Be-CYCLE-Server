package gdsc.sparkling_thon.book.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;
import gdsc.sparkling_thon.book.domain.enums.SearchCategoryEnum;
import gdsc.sparkling_thon.util.ImageUtil;
import gdsc.sparkling_thon.util.VisionClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gdsc.sparkling_thon.book.domain.dto.BookInfoDto;
import gdsc.sparkling_thon.book.domain.dto.PostCreateDto;
import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	/**
	 * @Author Minju_Kim
	 * 1. 책 이미지 등록
	 * 2. 이미지 기반 Google Vision API(OCR)로 텍스트 리턴
	 * 3. 리턴된 텍스트 기반 책 검색 (OriginalBookEntity)
	 * 4. 책 검색한 내용에서 사용자가 선택한 책 내용 -> 포스트 내용에 들어가게 됩니다.
	 */
	// OCR 결과로 책 검색
	@PostMapping("/search-by-ocr")
	public ResponseEntity<List<BookInfoDto>> searchBooksByOcr(@RequestParam("ocrText") String ocrText) {
		List<BookInfoDto> books = bookService.searchBooksByOcrResult(ocrText);
		return ResponseEntity.ok(books);
	}

	@GetMapping("")
	public ResponseEntity<List<BookInfoDto>> getBookList(
		@CookieValue(value = "userId", defaultValue = "defaultUserId") String userId,
		@RequestParam(value = "options",required = false) Set<SearchCategoryEnum> options
	) {
		List<BookEntity> books = bookService.getBooks(userId, options);
		List<BookInfoDto> bookInfoDtos = BookInfoDto.of(books);

		return ResponseEntity.ok(bookInfoDtos);
	}

	// 책 포스트 생성
	@PostMapping
	public ResponseEntity<BookInfoDto> createBookPost(@RequestBody PostCreateDto postCreateDto, @RequestParam String telNum) {
		// BookEntity를 BookInfoDto로 변환
		BookEntity createdBook = bookService.createBookPost(postCreateDto, telNum);
		BookInfoDto bookInfoDto = new BookInfoDto(createdBook);
		return ResponseEntity.ok(bookInfoDto);
	}

	@GetMapping("similar")
	public ResponseEntity<List<BookInfoDto>> getSimilarBooks(@RequestParam("image")MultipartFile image) throws IOException {
		String[] keywords = VisionClient.getTextFrom(ImageUtil.toByteString(image));
		List<OriginalBookEntity> similarBooks = bookService.getSimilarBooks(keywords);
		List<BookInfoDto> bookInfoDtos = BookInfoDto.ofOriginal(similarBooks);
		return ResponseEntity.ok(bookInfoDtos);
	}
}
