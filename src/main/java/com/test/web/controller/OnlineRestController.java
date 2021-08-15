package com.test.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.test.bean.StaticQueryParameter;
import com.test.bean.StaticQueryReport;
import com.test.bean.StaticQueryTemplate;
import com.test.util.FileIO;
import com.test.util.LoadProperties;

@RestController
@RequestMapping("/OnlineRestController")
public class OnlineRestController {

	@Autowired
	LoadProperties loadProperties;

	@Autowired
	FileIO fileIO;

	/**
	 * Test URL: http://localhost:8080/tm/OnlineRestController/start?name=dennis
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/start")
	public String start(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@RequestMapping("/getProperty")
	public String getProperty() {
		String s = "ejb.user = " + loadProperties.getProperty("DEV" + "." + "ejb.user");
		s += "<p>jdbc.user = " + loadProperties.getProperty("jdbc.user");
		s += "<p>page.title = " + loadProperties.getProperty("page.title");
		return s;
	}

	@RequestMapping("/loadStaticQueryReport")
	public String loadStaticQueryReport() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource("StaticQueryReport.txt");
		InputStream input = classPathResource.getInputStream();
		byte[] reportTemplate = input.readAllBytes();
		input.close();
		String reportTemplateStr = new String(reportTemplate, StandardCharsets.UTF_8);

		Object obj = new Gson().fromJson(reportTemplateStr, Object.class);
		System.out.println(obj);

		StaticQueryReport sqr = new Gson().fromJson(reportTemplateStr, StaticQueryReport.class);
		List<StaticQueryTemplate> templates = sqr.getStaticQueryTemplate();
		for (StaticQueryTemplate staticQueryTemplate : templates) {
			System.out.println("StaticQueryTemplate: " + staticQueryTemplate.getReportID() + ":" + staticQueryTemplate.getDescription());
			List<StaticQueryParameter> params = staticQueryTemplate.getStaticQueryParameter();
			for (StaticQueryParameter param : params) {
				System.out.println("    StaticQueryParameter: " + param.getName() + ":" + param.getLabel());
			}
		}
		return "Success";
	}

	@RequestMapping("/testFileIO")
	public String testFileIO() throws IOException {
		fileIO.classPathResourceRead();
		fileIO.resourceUtilsReader();
		fileIO.resourceUtilsWriter();
		return "success";
	}

	@RequestMapping("/readFileInJAR")
	public String readFileInJAR() throws IOException {
		fileIO.readFileInJAR();
		return "success";
	}

	@RequestMapping("/cacheFileToMemory")
	public String cacheFileToMemory() throws IOException {
		fileIO.cacheFileToMemory();
		return "success";
	}

	@RequestMapping("/readCacheFile")
	public String readCacheFile() throws IOException {
		return String.valueOf(FileIO.cacheFile.length);
	}

	@RequestMapping("/testClientRestful_Str")
	public String testClientRestful_Str() {
		final String url = "http://localhost:8080/tm/OnlineRestController/start?name=dennis";
		String result = new RestTemplate().getForObject(url, String.class);
		return result;
	}

	@RequestMapping("/testClientHeader_RestTemplate")
	public String testClientHeader_RestTemplate() throws JsonMappingException, JsonProcessingException {
		final String url = "http://localhost:8080/tm/JsonController/getKeyWithHeader_Server";

		HttpHeaders headers = new HttpHeaders();
		headers.set("testKey", "12345678");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("", headers);

		String data = new RestTemplate().postForObject(url, request, String.class);
		return data;
	}

	@RequestMapping("/testClientHeader_HttpURLConnection")
	public String testClientHeader_HttpURLConnection() {
		final String url = "http://localhost:8080/tm/JsonController/getKeyWithHeader_Server";
		BufferedReader result = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			String userCredentials = "testUser:password";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestProperty("testKey", "12345678");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			String input = "{\r\n" + "    \"name\" : \"MingTsui\",\r\n" + "    \"age\" : 40,\r\n"
					+ "    \"department\" : \"ABC\"\r\n" + "}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			result = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = result.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toString();
	}

}
