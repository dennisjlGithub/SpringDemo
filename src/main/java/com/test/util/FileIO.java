package com.test.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

/**
 * Spring boot默认对/**的访问可以直接访问四个目录下的文件： classpath:/public/ classpath:/resources/
 * classpath:/static/ classpath:/META-INFO/resouces/
 *
 */
@Component
public class FileIO {
	
	public static byte[] cacheFile;

	private static final String ClassPathResource_inputFile = "config/config.properties";
	private static final String ResourceUtils_inputFile = "classpath:config/config.properties";
	private static final String ResourceUtils_outputFile = "classpath:si/customerOutput.txt";

	public void classPathResourceRead() throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(ClassPathResource_inputFile);
		InputStream inputStream = classPathResource.getInputStream();
		this.read(new BufferedReader(new InputStreamReader(inputStream)));
	}

	public void resourceUtilsReader() throws IOException {
		File file = ResourceUtils.getFile(ResourceUtils_inputFile);
		this.read(new BufferedReader(new FileReader(file)));
	}

	public void resourceUtilsWriter() throws IOException {
		File file = ResourceUtils.getFile(ResourceUtils_outputFile);
		Writer writer = new FileWriter(file);
		writer.write("test!");
		writer.close();
	}

	public byte[] readFileToByteArray(String inputFilePath) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(inputFilePath);
		InputStream inputStream = classPathResource.getInputStream();
		byte[] result = inputStream.readAllBytes();
		inputStream.close();
		return result;
	}

	public void readFileInJAR() throws IOException {
		InputStream inputStream = FileIO.class.getResourceAsStream("/META-INF/LICENSE.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();

		File file = new File(FileIO.class.getResource("/META-INF/LICENSE.txt").getFile());
		System.out.println("fileSize = " + file.canRead());
	}

	public void cacheFileToMemory() throws IOException {
		InputStream inputStream = new ClassPathResource(ClassPathResource_inputFile).getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[512];
		int len = -1;
		while ((len = inputStream.read(data)) != -1)
			baos.write(data, 0, len);
		cacheFile = baos.toByteArray();
		baos.close();
		inputStream.close();
	}

	private void read(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null) {
			System.out.println(line);
			line = reader.readLine();
		}
		reader.close();
	}

}
