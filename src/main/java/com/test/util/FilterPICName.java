package com.test.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilterPICName {
	private static final String rootPath = "D:\\Download\\";
	private static final String suffix = "*.{jpg,ico,gif}";

	public static void main(String[] args) throws IOException {
		FilterPICName.removeFiles();
	}

	public static void removeFiles() throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(rootPath))) {
			for (Path path : stream) {
				if (Files.isDirectory(path)) {
					String dir = path.getFileName().toString();
					System.out.println("Process folder: " + dir);
					FilterPICName.removeFilesbyFolder(rootPath + dir);
				}
			}
		}
	}

	public static void removeFilesbyFolder(String dir) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(dir), suffix)) {
			for (Path file : stream) {
				String fileName = file.getParent() + "\\" + file.getFileName();
				if (fileName.endsWith(".ico") || fileName.endsWith(".gif") || fileName.endsWith("(1).jpg") || fileName.contains("\\0")) {
					Files.deleteIfExists(Paths.get(fileName));
				}
			}
		}
	}

}
