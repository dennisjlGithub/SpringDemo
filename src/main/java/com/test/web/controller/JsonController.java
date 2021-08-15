package com.test.web.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.JsonCustomer;
import com.test.bean.ReportVO;
import com.test.util.FileIO;

/**
 * Test URL: http://localhost:8080/tm/JsonController/getByteReport
 */ 

@RestController
@RequestMapping("/JsonController")
public class JsonController {

	public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

	public static final String HOST = "http://localhost:8099";
//	public static final String HOST = "http://bvba15552:8099";

	@Autowired
	FileIO fileIO;

	@RequestMapping("/testJsonObject")
	public JsonCustomer testJsonObject() {
		JsonCustomer customer = new JsonCustomer();
		customer.setEngName("dennis");
		customer.setCnName("测试");
		customer.setAge(30);
		customer.setBirthday(LocalDate.of(1980, 1, 1));
		return customer;
	}

	@RequestMapping("/testJsonList")
	public List<JsonCustomer> testJsonList() {
		List<JsonCustomer> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			JsonCustomer customer = new JsonCustomer();
			customer.setEngName("dennis" + i);
			customer.setCnName("测试");
			customer.setAge(30);
			customer.setBirthday(LocalDate.of(1980, 1, 1));
			users.add(customer);
		}
		return users;
	}

	/**
	 * Use Postman tool send the json format request. RequestBody 与 RequestParam 区别:
	 * RequestParam接收的参数是来自requestHeader中，即请求头。 处理 Content-Type 为
	 * application/x-www-form-urlencoded 编码的内容
	 * RequestBody接收的参数是来自requestBody中，即请求体。一般用于处理 application/json,
	 * application/xml等类型的数据。 通过RequestBody可以解析Body中json格式的数据。
	 * 
	 * @param customer
	 * @return
	 */
	@PostMapping(value = "/testJsonparam")
	public JsonCustomer testJsonparam(@RequestBody JsonCustomer customer) {
		System.out.println("testJsonparam : " + customer.getCnName());
		return customer;
	}

	@RequestMapping("/testClientRestful_sendObject")
	public String testClientRestful_sendObject() throws JSONException {
		final String url = "http://localhost:8080/tm/JsonController/testJsonparam";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("engName", "test");
		// jsonObj.put("birthday", LocalDate.of(1980, 1, 1));
		jsonObj.put("cnName", "測試");
		jsonObj.put("age", 10);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(jsonObj.toString(), headers);

		String result = new RestTemplate().postForObject(url, request, String.class);
		return result;
	}

	@RequestMapping("/testClientRestful_getObject")
	public JsonCustomer testClientRestful_getObject() {
		final String url = "http://localhost:8080/tm/JsonController/testJsonObject";
		JsonCustomer json = new RestTemplate().getForObject(url, JsonCustomer.class);
		return json;
	}

	@RequestMapping("/testClinetJason_getList")
	public List<?> testClinetJason_getList() {
		final String url = "http://localhost:8080/tm/JsonController/testJsonList";
		List<?> list = new RestTemplate().getForObject(url, List.class);
		list.forEach(content -> {
			System.out.println(content);
		});
		return list;
	}

	/**
	 * Prepare parameters, send to file server.
	 * 
	 * @param response
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = "/downloadFileClientByJson", produces = MediaType.APPLICATION_PDF_VALUE)
	public byte[] downloadFileClientByJson(RedirectAttributes attributes) throws JSONException {

		String url = "http://localhost:8080/tm/JsonController/downloadFileServerByJson";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("reportID", "si/test.pdf");
		jsonObj.put("buzDate", "01/01/2020");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(jsonObj.toString(), headers);

		return new RestTemplate().postForObject(url, request, byte[].class);
	}

	/**
	 * Returning Raw Data: MediaType.APPLICATION_OCTET_STREAM_VALUE
	 * Returning CSV Data: cannot support. should download.
	 */
	@RequestMapping(value = "/downloadFileServer", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] downloadFileServer(@RequestParam String fileName) throws IOException {
		System.out.println(fileName);
		byte[] byteArray = fileIO.readFileToByteArray(fileName);
		return byteArray;
	}

	/**
	 * 
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadFileServerByJson", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] downloadFileServerByJson(@RequestBody ReportVO reportVO)
			throws JSONException, IOException {
		System.out.println("report file = " + reportVO.getReportID());
		byte[] byteArray = fileIO.readFileToByteArray(reportVO.getReportID());
		return byteArray;
	}

	@RequestMapping(value = "/getByteReport", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getByteReport() throws IOException {
		final String url = HOST + "/emms-dtms-rpt/ReportController/onlineByteReport";
		List<HashMap<String, String>> parameters = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> params = new HashMap<>();
		params.put("revcycle", "201704");
		parameters.add(params);

		ReportVO report = new ReportVO();
		report.setReportID("TM026R04");
		report.setReportFormat("2");
		report.setDatasource("1");
		report.setSplitFile("N");
		report.setToken("11111111");
		report.setBuzDate("11/05/2017");
		report.setParameters(parameters);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInputStr = objectMapper.writeValueAsString(report);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println("====================");
		System.out.println(jsonInputStr);
		System.out.println("====================");
		HttpEntity<String> request = new HttpEntity<String>(jsonInputStr, headers);

		return new RestTemplate().postForObject(url, request, byte[].class);
	}

	@RequestMapping(value = "/getJsonReport", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getJsonReport() throws IOException {
		final String url = HOST + "/emms-dtms-rpt/ReportController/onlineJsonReport";

		List<HashMap<String, String>> parameters = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> params = new HashMap<>();
		params.put("revcycle", "201704");
		parameters.add(params);

		ReportVO report = new ReportVO();
		report.setReportID("TM026R04");
		report.setReportFormat("2");
		report.setDatasource("1");
		report.setSplitFile("N");
		report.setToken("11111111");
		report.setBuzDate("11/05/2017");
		report.setParameters(parameters);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInputStr = objectMapper.writeValueAsString(report);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println("====================");
		System.out.println(jsonInputStr);
		System.out.println("====================");
		HttpEntity<String> request = new HttpEntity<String>(jsonInputStr, headers);

		String data = new RestTemplate().postForObject(url, request, String.class);
		HashMap<?, ?> dataMap = objectMapper.readValue(data, HashMap.class);
		ArrayList<?> reportDataList = (ArrayList<?>) dataMap.get("reportData");
		byte[] byteArray = new byte[reportDataList.size()];
		for (int i = 0; i < reportDataList.size(); i++) {
			byteArray[i] = ((Integer) reportDataList.get(i)).byteValue();
		}

		// return Base64.getDecoder().decode(byteArray);
		return byteArray;
	}

	/**
	 * useless
	 */
	@RequestMapping("/getKeyWithHeader_Server")
	public String getKeyWithHeader_Server(@RequestHeader("testKey") String testKey) {
		System.out.println("getKeyWithHeader_Server : " + testKey);
		return testKey;
	}

	/**
	 * useless
	 */
	@RequestMapping("/getKeyWithHeaderAll_Server")
	public Map<String, String> getKeyWithHeaderAll_Server(@RequestHeader Map<String, String> headers) {
		headers.forEach((key, value) -> {
			System.out.println(String.format("Header '%s' = %s", key, value));
		});
		return headers;
	}
}
