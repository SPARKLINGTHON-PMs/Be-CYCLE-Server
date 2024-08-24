package gdsc.sparkling_thon.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class ImageUtil {
    public String resizeImage(MultipartFile file) {
        try {
            return Base64.getEncoder().encodeToString(file.getBytes()).replaceAll("\n", "");
        } catch (IOException e) {
            System.out.println("이미지 리사이즈에 실패했습니다.");
        }
        return null;
    }
}