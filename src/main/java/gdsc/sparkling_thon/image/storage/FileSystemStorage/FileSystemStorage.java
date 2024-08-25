package gdsc.sparkling_thon.image.storage.FileSystemStorage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import gdsc.sparkling_thon.image.storage.core.StoragePacket;
import gdsc.sparkling_thon.image.storage.core.StorageSaveResultInternal;
import gdsc.sparkling_thon.image.storage.core.StorageService;
import gdsc.sparkling_thon.image.storage.core.StorageType;
import gdsc.sparkling_thon.image.storageManager.common.StorageFindResult;
import lombok.NonNull;

@Service
public class FileSystemStorage implements StorageService {

	private final Path rootLocation = Paths.get("data/images");

	@Override
	public StorageSaveResultInternal save(@NonNull StoragePacket packet) {

		if (packet.isPayloadEmpty()) {
			throw new IllegalArgumentException("파일이 비어 있습니다.");
		}

		if (Files.notExists(this.rootLocation)) {
			throw new IllegalStateException("디렉토리에 접근할 수 없습니다.");
		}

		try {
			Path destination = this.rootLocation
				.resolve(packet.getDestinationPath())
				.normalize()
				.toAbsolutePath();

			if (Files.notExists(destination.getParent())) {
				this.createDirectory(destination.getParent());
			}

			Files.copy(packet.getFileData().getInputStream(), destination);

			return new StorageSaveResultInternal(
				StorageType.LOCAL_FILE_SYSTEM,
				destination
			);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 중 오류가 발생했습니다: " + e.getMessage(), e);
		}
	}

	@Override
	public StorageFindResult load(@NonNull String filePath) {
		try {
			Path fullPath = this.rootLocation.resolve(filePath).normalize().toAbsolutePath();
			Resource resource = new UrlResource(fullPath.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				throw new IllegalStateException("파일을 읽을 수 없습니다.");
			}

			return new StorageFindResult(
				StorageType.LOCAL_FILE_SYSTEM,
				resource
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException("파일 경로가 잘못되었습니다: " + e.getMessage(), e);
		}
	}
	// 새롭게 추가된 부분 🎀
	@Override
	public void delete(String filePath) {
		try {
			Path fullPath = this.rootLocation.resolve(filePath).normalize().toAbsolutePath();
			Files.deleteIfExists(fullPath);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to delete file", e);
		}
	}

	private void createDirectory(@NonNull Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new RuntimeException("디렉토리 생성 중 오류가 발생했습니다: " + e.getMessage(), e);
		}
	}
}
