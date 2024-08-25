package gdsc.sparkling_thon.book.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gdsc.sparkling_thon.book.domain.enums.SearchCategoryEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import gdsc.sparkling_thon.book.domain.dto.BookInfoDto;
import gdsc.sparkling_thon.book.domain.dto.PostCreateDto;
import gdsc.sparkling_thon.book.service.BookService;
import gdsc.sparkling_thon.util.ImageUtil;
import gdsc.sparkling_thon.util.VisionClient;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
	private final VisionClient visionClient;
	private final BookService bookService;


	// OCR 결과로 JSON 응답 반환
	@PostMapping("/upload")
	public ResponseEntity<String> uploadAndSearchBooks(@RequestPart("image") MultipartFile image) {
		try {
			// 이미지를 Base64로 변환
			String base64Image = ImageUtil.toByteString(image);

			// VisionClient를 사용하여 JSON 응답 반환
			String jsonResponse = visionClient.getJsonResponseFromImage(base64Image);

			// JSON 응답 반환
			return ResponseEntity.ok(jsonResponse);

		} catch (IOException e) {
			// 오류 발생 시 에러 메시지 반환
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "OCR 처리 중 오류가 발생했습니다.");
			return ResponseEntity.status(500).body(errorResponse.toString());
		}
	}


/*	// OCR 결과로 책 검색
	@PostMapping("/upload-and-search")
	public ResponseEntity<List<BookInfoDto>> uploadImageAndSearchBooks(@RequestPart("image") MultipartFile image) {
		try {
			// 이미지를 OCR하여 텍스트 추출
			String base64Image = ImageUtil.toByteString(image);
			String[] ocrTexts = visionClient.getTextFrom(base64Image);

			// OCR 결과로 책 검색
			List<BookInfoDto> books = bookService.searchBooksByOcrResults(ocrTexts);

			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.status(500).build(); // 예외 발생 시 500 에러 반환
		}
	}*/

	@GetMapping("")
	public ResponseEntity<List<BookInfoDto>> getBookList(
		@CookieValue(value = "userId", defaultValue = "defaultUserId") String userId,
		@RequestParam(value = "options", required = false) Set<SearchCategoryEnum> options
	) {
		List<BookInfoDto> bookInfoDtos = bookService.getBooks(userId, options);
		return ResponseEntity.ok(bookInfoDtos);
	}

	// 책 포스트 생성
	@PostMapping
	public ResponseEntity<BookInfoDto> createBookPost(@RequestPart(value = "create_dto") PostCreateDto postCreateDto,
		@RequestParam String telNum,
		@RequestPart(value = "image") MultipartFile image) {
		BookInfoDto bookInfoDto = bookService.createBookPost(postCreateDto, telNum, image);
		return ResponseEntity.ok(bookInfoDto);
	}
}
