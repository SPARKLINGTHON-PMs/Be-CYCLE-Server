package gdsc.sparkling_thon.image.storage.core;

import gdsc.sparkling_thon.image.storageManager.common.StorageFindResult;

public interface StorageService {

	StorageSaveResultInternal save(StoragePacket packet);

	StorageFindResult load(String fileUrl);

	// 새롭게 추가된 부분 🎀
	void delete(String filePath);
}
