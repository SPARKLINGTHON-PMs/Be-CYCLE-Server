package gdsc.sparkling_thon.image.storage.core;

import java.nio.file.Path;

import lombok.NonNull;

public record StorageSaveResultInternal(
	@NonNull StorageType storageType,
	@NonNull Path savedPath
) {}
