package com.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilterPICName {
	private static final String rootPath = "D:\\Temp\\Done\\a";
	private static final String suffix = "*.{jpg,jar,txt,pdf}";
	
	public static void main(String[] args) {
		
		RenameFiles.listFileNames(rootPath);
//		RenameFiles.rename1(rootPath);
		
	}
	
	public static void listFileNames(String filePath) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(filePath), suffix)) {
		    for (Path file: stream) {
		        System.out.println(file.getParent() + "\\"+ file.getFileName());
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
	}
	
	public static void rename(String filePath, String newName) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(filePath), suffix)) {
		    for (Path file: stream) {
		        File oldFile = new File(file.getParent() + "\\" + file.getFileName());
		        File newFile = new File(file.getParent() + "\\" + newName + file.getFileName());
		        oldFile.renameTo(newFile);
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
	}
	
	public static void rename1(String filePath) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(filePath), suffix)) {
		    for (Path file: stream) {
		        File oldFile = new File(file.getParent() + "\\" + file.getFileName());
		        File newFile = new File(file.getParent() + "\\" + "2020_" + file.getFileName().toString().substring(10, 12) + ".pdf");
		        oldFile.renameTo(newFile);
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
	}
		
}
