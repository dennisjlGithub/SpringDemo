package com.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenameFiles {
	private static final String rootPath = "E:\\Done\\test";
	private static final String suffix = "*.{jpg,jar,txt,pdf,mp4}";
	
	public static void main(String[] args) {
		
		RenameFiles.listFileNames(rootPath);
		RenameFiles.rename_removeH265(rootPath);
		
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
	
	public static void rename_removeH265(String filePath) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(filePath), suffix)) {
		    for (Path file: stream) {
		    	String fileName = file.getFileName().toString();
		        File oldFile = new File(file.getParent() + "\\" + fileName);
		        File newFile = new File(file.getParent() + "\\" + fileName.replaceAll("_H.265", ""));
		        oldFile.renameTo(newFile);
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
	}
		
}
