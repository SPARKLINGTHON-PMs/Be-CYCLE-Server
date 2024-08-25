package gdsc.sparkling_thon.book.controller;


import gdsc.sparkling_thon.book.service.OcrService;
import gdsc.sparkling_thon.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
@RequiredArgsConstructor
public class OcrController {

	private final OcrService ocrService;

	@PostMapping("/extract-text")
	public ResponseEntity<String[]> extractText(@RequestPart(value = "image") MultipartFile image) {
		try {
			String base64Image = ImageUtil.toByteString(image); // MultipartFile을 Base64로 변환
			String[] extractedText = ocrService.extractTextFromImage(base64Image);
			return ResponseEntity.ok(extractedText);
		} catch (IOException e) {
			return ResponseEntity.status(500).body(new String[]{"텍스트 추출에 실패했습니다."});
		}
	}
}