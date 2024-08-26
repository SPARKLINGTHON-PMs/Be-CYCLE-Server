package gdsc.sparkling_thon.image.storageManager;

import org.springframework.web.multipart.MultipartFile;

import gdsc.sparkling_thon.image.storage.core.StorageType;
import gdsc.sparkling_thon.image.storageManager.common.StorageFindResult;
import gdsc.sparkling_thon.image.storageManager.common.StorageSaveResult;

public interface StorageManager {


	StorageSaveResult saveResource(MultipartFile resource, StorageType storageType);


	StorageFindResult findResourceById(Long resourceId);
}
