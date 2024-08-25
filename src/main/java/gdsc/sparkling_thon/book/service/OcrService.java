package gdsc.sparkling_thon.book.service;

import gdsc.sparkling_thon.util.VisionClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OcrService {

	public String[] extractTextFromImage(String base64Image) throws IOException {
		return VisionClient.getTextFrom(base64Image);
	}
}