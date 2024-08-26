package gdsc.sparkling_thon.image.storageManager.common;

import gdsc.sparkling_thon.image.storage.core.StorageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StorageSaveResult {
	private final StorageType storageType;
	private final Long resourceLocationId;
}
