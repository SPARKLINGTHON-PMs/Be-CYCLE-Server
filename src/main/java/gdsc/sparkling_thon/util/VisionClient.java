package gdsc.sparkling_thon.util;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VisionClient {

    private static final Logger logger = Logger.getLogger(VisionClient.class.getName());

    public static String[] getTextFrom(String base64Image) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(Base64.getDecoder().decode(base64Image));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            // Error handling if the API response contains errors
            if (responses.get(0).hasError()) {
                logger.log(Level.SEVERE, "Error: {0}", responses.get(0).getError().getMessage());
                return new String[] {"Error occurred during OCR processing"};
            }

            // Returning the extracted text
            return responses.get(0).getTextAnnotations(0).getDescription().split("\n");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to process image with Vision API", e);
            throw e;
        }
    }

    public String getJsonResponseFromImage(String base64Image) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(Base64.getDecoder().decode(base64Image));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            if (responses.get(0).hasError()) {
                logger.log(Level.SEVERE, "Error: {0}", responses.get(0).getError().getMessage());
                return "{\"error\": \"" + responses.get(0).getError().getMessage() + "\"}";
            }

            // Returning the JSON response from the Vision API
            return responses.get(0).toString();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to process image with Vision API", e);
            throw e;
        }
    }
}