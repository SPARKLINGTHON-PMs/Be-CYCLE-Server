package gdsc.sparkling_thon.image.storageManager.imageStorageManager;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gdsc.sparkling_thon.image.resourceLocation.entity.ResourceLocationEntity;
import gdsc.sparkling_thon.image.resourceLocation.repository.ResourceLocationRepository;
import gdsc.sparkling_thon.image.storage.core.StoragePacket;
import gdsc.sparkling_thon.image.storage.core.StorageSaveResultInternal;
import gdsc.sparkling_thon.image.storage.core.StorageService;
import gdsc.sparkling_thon.image.storage.core.StorageType;
import gdsc.sparkling_thon.image.storage.strategy.LocalDateDirectoryNamingStrategy;
import gdsc.sparkling_thon.image.storage.strategy.UuidV4FileNamingStrategy;
import gdsc.sparkling_thon.image.storageManager.StorageManager;
import gdsc.sparkling_thon.image.storageManager.common.StorageFindResult;
import gdsc.sparkling_thon.image.storageManager.common.StorageSaveResult;
import lombok.NonNull;

@Service
public class ImageStorageManager implements StorageManager {

	private final ResourceLocationRepository imageRepository;
	private final StorageService storageService;

	public ImageStorageManager(
		ResourceLocationRepository resourceLocationRepository,
		StorageService storageService
	) {
		this.imageRepository = resourceLocationRepository;
		this.storageService = storageService;
	}

	@Override
	public StorageSaveResult saveResource(
		@NonNull MultipartFile file,
		StorageType storageType
	) {
		StoragePacket packet = StoragePacket
			.builder()
			.fileData(file)
			.fileNamingStrategy(new UuidV4FileNamingStrategy())
			.directoryNamingStrategy(new LocalDateDirectoryNamingStrategy())
			.build();

		StorageSaveResultInternal storageSaveResult = switch (storageType) {
			case LOCAL_FILE_SYSTEM -> this.storageService.save(packet);
			// case AWS_S3_STORAGE -> TODO: implementation required
			// case MINIO_STORAGE -> TODO: implementation required
			// case IN_MEMORY -> TODO: implementation required
			default -> throw new UnsupportedOperationException("[미구현 기능] Unsupported storage type: " + storageType);
		};

		ResourceLocationEntity savedImageLocation = this.imageRepository.save(
			ResourceLocationEntity
				.builder()
				.storageType(storageSaveResult.storageType())
				.savedPath(storageSaveResult.savedPath().toString())
				.build()
		);

		return new StorageSaveResult(
			storageSaveResult.storageType(),
			savedImageLocation.getResourceLocationId()
		);
	}

	@Override
	public StorageFindResult findResourceById(Long imageId) {
		return this.imageRepository.findById(imageId)
			.map(
				image -> this.storageService.load(image.getSavedPath())
			)
			.orElseThrow(() ->
				new IllegalArgumentException("Resource location not found for ID: " + imageId)
			);
	}

	// 새롭게 추가된 부분 🎀
	public void deleteResourceById(Long resourceLocationId) {
		ResourceLocationEntity resourceLocation = this.imageRepository.findById(resourceLocationId)
			.orElseThrow(() -> new IllegalArgumentException("Resource not found"));

		this.storageService.delete(resourceLocation.getSavedPath());
		this.imageRepository.delete(resourceLocation);
	}
}
