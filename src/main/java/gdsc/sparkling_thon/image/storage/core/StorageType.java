package gdsc.sparkling_thon.image.storage.core;

public enum StorageType {
	/**
	 * 서버 로컬 파일 시스템을 스토리지로 사용합니다.
	 */
	@Deprecated
	LOCAL_FILE_SYSTEM,

	/**
	 * @deprecated
	 *  Not Implemented
	 */
	@Deprecated
	IN_MEMORY,

	/**
	 * @deprecated
	 *  Not Implemented
	 */
	@Deprecated
	AWS_S3_STORAGE,

	/**
	 * @deprecated
	 *  Not Implemented
	 */
	@Deprecated
	MINIO_STORAGE
}
