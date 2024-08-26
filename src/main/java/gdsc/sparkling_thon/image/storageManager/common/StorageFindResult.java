package gdsc.sparkling_thon.image.storageManager.common;

import org.springframework.core.io.Resource;

import gdsc.sparkling_thon.image.storage.core.StorageType;
import lombok.NonNull;

/**
 * @param storageType 저장소 종류 (FileSystem | AmazonS3 | etc ...)
 * @param resource 저장된 Blob 파일 데이터
 */
public record StorageFindResult(
	@NonNull StorageType storageType,
	@NonNull Resource resource
) {
}
