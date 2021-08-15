package com.test.util;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadLocalFile {
	private static final String filePath = "D:/document-merge-tm.xml";

	public static void main(String[] args) {
		ReadLocalFile.readFile();
	}

	public static void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(content -> {
				if (content.contains("<template id=\"")) {
					content = content.trim();
					content = content.substring("		<template id=\"".length()-2);
					content = content.replaceAll("\">", ".builder=");
					System.out.print(content);
				}
				else if (content.contains("<template id='")) {
					content = content.trim();
					content = content.substring("		<template id='".length()-2);
					content = content.replaceAll("'>", ".builder=");
					System.out.print(content);
				}
				else if (content.contains("builder.impl.")) {
					content = content.trim();
					content = content.substring(content.indexOf(".builder.impl.") + ".builder.impl.".length());
					content = content.replaceAll("</builder-class>", "");
					System.out.println(content);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void listFileNamesWithRecursion(String path) throws Exception {
		try (Stream<Path> paths = Files.walk(Paths.get(path))) {
			paths
		    .filter(Files::isRegularFile)
		    .forEach(s -> {
		    	if (s.endsWith(".txt"))
		    	System.out.println(s);
		    });
		}
	}
	
	public static void listFileNames(String filePath) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(filePath), "*.{jpg,jar,txt,pdf}")) {
		    for (Path file: stream) {
		        System.out.println(file.getParent() + "\\"+ file.getFileName());
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
	}
}
