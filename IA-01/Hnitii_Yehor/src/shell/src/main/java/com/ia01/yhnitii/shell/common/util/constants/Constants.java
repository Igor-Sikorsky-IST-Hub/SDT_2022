package com.ia01.yhnitii.shell.common.util.constants;

public interface Constants {

	interface FileSystem {
		String PATH_IS_NOT_FOLDER = "No folder find in specified path";
		String DELETION_FAILED = "Deletion of: %s failed";
		String COPY_FAILED = "Copy from: %s, to: %s failed";
		String UPLOAD_FAILED = "Failed to upload file";
		String FILE_EXISTS = "File/directory already exists: %s";
		String SEARCH_FAILED = "Search for path: %s failed";
		String ZIP_FAILED = "Zipping failed";
		String DOWNLOAD_FAILED = "Download failed";
		String DIRECTORY_SIZE_HIGH_TO_ZIP = "Directory size is too high for zip, max size is: %sGB";
	}

	interface History {
		String ILLEGAL_ARGUMENTS = "Can't save history because, illegal arguments passed";
	}

	interface CachedZip {
		String CACHED_ZIP_NOT_FOUND = "Cached zip not found";
	}

}
