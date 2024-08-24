package gdsc.sparkling_thon.util;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.AnnotateImageResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class VisionClient {
    public static String[] getTextFrom(String buffer) throws IOException {
        String[] textList;
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(Base64.getDecoder().decode(buffer));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // 요청을 보내는 데 사용할 클라이언트를 초기화합니다.
        // 이 클라이언트는 한 번만 만들기만 하면 되고 여러 번의 요청에 재사용할 수 있습니다.
        // 모든 요청을 완료한 후 클라이언트에서 "닫기" 메서드를 호출하여 남아 있는 백그라운드 리소스를 안전하게 정리합니다.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            textList = responses.get(0).getTextAnnotations(0).getDescription().split("\n");
        }
        return textList;
    }
}