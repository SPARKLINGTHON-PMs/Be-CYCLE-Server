package gdsc.sparkling_thon.image.controller;

import gdsc.sparkling_thon.image.storageManager.common.StorageFindResult;
import gdsc.sparkling_thon.image.storageManager.imageStorageManager.ImageStorageManager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/a1")
public class ImagePublicResourceController {

	private final ImageStorageManager imageStorageManager;

	public ImagePublicResourceController(@Qualifier("imageStorageManager") ImageStorageManager imageStorageManager) {
		this.imageStorageManager = imageStorageManager;
	}


	@GetMapping("/image/{resourceId}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(
		@PathVariable Long resourceId
	) {
		StorageFindResult result = this.imageStorageManager.findResourceById(resourceId);

		return ResponseEntity.ok().header(
			HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + result.resource().getFilename() + "\""
		).body(result.resource());
	}
}
