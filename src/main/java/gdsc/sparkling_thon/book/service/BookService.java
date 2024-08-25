package gdsc.sparkling_thon.book.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import gdsc.sparkling_thon.image.storage.core.StorageType;
import gdsc.sparkling_thon.image.storageManager.common.StorageSaveResult;
import gdsc.sparkling_thon.image.storageManager.imageStorageManager.ImageStorageManager;
import gdsc.sparkling_thon.image.tools.Base64Converter;
import gdsc.sparkling_thon.user.domain.UserEntity;
import gdsc.sparkling_thon.user.repository.UserRepository;
import gdsc.sparkling_thon.util.VisionClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final DefaultBookRepository defaultBookRepository;
	private final UserRepository userRepository;
	private final OriginalBookRepository originalBookRepository;
	private final CategoryRepository categoryRepository;
	private final ImageStorageManager imageStorageManager;
	private final VisionClient visionClient;


	@Transactional
	public BookInfoDto createBookPost(PostCreateDto bookCreateDto, String telNum, MultipartFile image) {
		UserEntity user = userRepository.findByTelNum(telNum)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		OriginalBookEntity originalBook = originalBookRepository.findById(bookCreateDto.getOriginalBookId())
			.orElseThrow(() -> new IllegalArgumentException("OriginalBook not found"));

		// 이미지 저장 처리 🎀
		StorageSaveResult storageSaveResult;
		try {
			storageSaveResult = imageStorageManager.saveResource(image, StorageType.LOCAL_FILE_SYSTEM);
		} catch (Exception e) {
			throw new IllegalStateException("Image save failed", e);
		}

		// BookEntity 생성 및 저장 🎀
		BookEntity book = BookEntity.builder()
			.user(user)
			.originalBook(originalBook)
			.imageId(storageSaveResult.getResourceLocationId())  // 이미지 ID 설정
			.status(bookCreateDto.getStatus())
			.tradeState(bookCreateDto.getTradeState())
			.build();

		try {
			BookEntity savedBook = bookRepository.save(book);  // 먼저 저장하여 ID를 생성

			// 카테고리와 연관관계 설정 🎀
			Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(bookCreateDto.getCategoryIds()));
			for (CategoryEntity category : categories) {
				savedBook.addCategory(category);
			}

			// 모든 연관관계 설정 후 책 엔티티를 다시 저장 🎀
			savedBook = bookRepository.save(book);

			return new BookInfoDto(savedBook);
		} catch (Exception e) {
			// 포스트 저장 실패 시, 이미 저장된 이미지를 삭제
			imageStorageManager.deleteResourceById(storageSaveResult.getResourceLocationId());
			throw new IllegalStateException("Post save failed", e);
		}
	}

	// 책 리스트 가져오기
	public List<BookInfoDto> getBooks(String userId, Set<SearchCategoryEnum> options) {
		List<BookEntity> books;

		if (options == null || options.isEmpty()) {
			// 옵션이 없을 경우, 모든 책을 가져옵니다.
			books = defaultBookRepository.getBooks(userId, null, 0, 0);
		} else {
			// 옵션이 있을 경우, 해당 옵션에 맞는 책을 가져옵니다.
			books = defaultBookRepository.getBooks(userId, options.stream().collect(Collectors.toSet()), 0, 0);
		}

		return books.stream().map(BookInfoDto::new).collect(Collectors.toList());
	}



/*	@Transactional
	public List<BookInfoDto> handleImageUploadAndSearch(MultipartFile image, String telNum) throws IOException {
		// 1. 이미지 저장 처리 🎀
		StorageSaveResult storageSaveResult = imageStorageManager.saveResource(image, StorageType.LOCAL_FILE_SYSTEM);

		// 2. Base64 인코딩 처리 🎀
		String base64Image = Base64Converter.convertToBase64(image);

		// 3. OCR 작업 수행 🎀
		String[] ocrTexts = visionClient.getTextFrom(base64Image);

		// 4. OCR 결과를 바탕으로 DB에서 책 정보 검색 🎀
		List<BookInfoDto> matchedBooks = searchBooksByOcrResults(ocrTexts);

		// 5. 검색 결과 반환 🎀
		return matchedBooks;
	}*/

	// 책 정보를 OCR 텍스트를 바탕으로 검색하는 로직
	public List<BookInfoDto> searchBooksByOcrResults(String[] ocrTexts) {
		List<OriginalBookEntity> matchedBooks = new ArrayList<>();
		for (String ocrText : ocrTexts) {
			List<OriginalBookEntity> booksByTitle = originalBookRepository.findByTitleContainingIgnoreCase(ocrText);
			List<OriginalBookEntity> booksByAuthor = originalBookRepository.findByAuthorContainingIgnoreCase(ocrText);
			matchedBooks.addAll(booksByTitle);
			matchedBooks.addAll(booksByAuthor);
		}
		return matchedBooks.stream().distinct().map(BookInfoDto::new).collect(Collectors.toList());
	}
}
