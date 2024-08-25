package gdsc.sparkling_thon.image.storage.strategy;

import java.time.LocalDate;

public class LocalDateDirectoryNamingStrategy implements DirectoryNamingStrategy {

	@Override
	public String getDirectory() {
		return LocalDate.now().toString();
	};
}
