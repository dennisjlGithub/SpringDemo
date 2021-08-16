package com.test.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.StaticQueryReport;
import com.test.web.form.OnlineForm;

/**
 * http://localhost:8080/tm/OnlineController/start
 * 
 * @author DD
 *
 */
@Controller
@RequestMapping("/OnlineController")
@SessionAttributes("onlineForm")
public class OnlineController {

	@Autowired
	private MessageSource messageSource;

	@ModelAttribute("onlineForm")
	public OnlineForm getSystemHealthCheckForm() {
		return new OnlineForm();
	}

	/**
	 * Prepare parameters, send to file server. Can use "redirect:" as well: return
	 * "redirect:http://localhost:8080/tm/JsonController/downloadFileServer"
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downloadFileClient")
	public RedirectView downloadFileClient(RedirectAttributes attributes) throws IOException {
		attributes.addAttribute("fileName", "si/test.pdf");
		return new RedirectView("http://localhost:8080/tm/JsonController/downloadFileServer");
	}

	@RequestMapping("/start")
	public String start(@ModelAttribute("onlineForm") OnlineForm onlineForm, HttpServletRequest request)
			throws Exception {
		// Json Data
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("reportID1", "si/test.pdf");
		jsonObj.put("buzDate", "01/01/2020");
		onlineForm.setJsonStr(jsonObj.toString());

		// drop down list
		Map<String, String> newCityList = new HashMap<>();
		newCityList.put("BJ", "北京");
		newCityList.put("SH", "上海");
		newCityList.put("GZ", "广州");
		onlineForm.setCityList(newCityList);

		request.setAttribute("apostrophe1",
				messageSource.getMessage("test.apostrophe1", new String[] { "Dennis1" }, null));
		request.setAttribute("apostrophe2",
				messageSource.getMessage("test.apostrophe2", new String[] { "Dennis2" }, null));

		// Checkbox
		String[] books = new String[] { "JAVA", "NET", "PYTHON", "Spring Framework" };
		request.setAttribute("books", books);
		onlineForm.setFavoriteBooks(new String[] { "JAVA", "NET" });

		return "welcome";
	}

	@RequestMapping("/toSubmit")
	public String submit(@ModelAttribute("onlineForm") OnlineForm onlineForm, HttpServletRequest request)
			throws Exception {

		return "submit";
	}

	@PostMapping("/fileUpload")
	public String fileUpload(@RequestParam("uploadFile") MultipartFile file,
			@ModelAttribute("onlineForm") OnlineForm onlineForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("File size is: " + file.getSize());
		System.out.println("File contentType is: " + file.getContentType());
		System.out.println("File name is: " + file.getName());
		System.out.println("File originalFilename is: " + file.getOriginalFilename());
		onlineForm.setInputFile(file.getBytes());
		onlineForm.setInputFileName(file.getOriginalFilename());
		onlineForm.setInputFileType(file.getContentType());
		return "printFile";
	}

	@PostMapping("/printFileByAJAX")
	@ResponseBody
	public byte[] printFileByAJAX(@ModelAttribute("onlineForm") OnlineForm onlineForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("File name = " + onlineForm.getInputFileName());
		System.out.println("File size = " + onlineForm.getInputFile().length);
		System.out.println("File contentType = " + onlineForm.getInputFileType());
		response.addHeader("fileMIMEType", onlineForm.getInputFileType());
		return onlineForm.getInputFile();
	}

	@RequestMapping("/printFileBySpring")
	public ResponseEntity<byte[]> printFileBySpring(@ModelAttribute("onlineForm") OnlineForm onlineForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = onlineForm.getInputFileName();
		String[] temp = fileName.split("\\.");
		String fileType = temp[temp.length - 1].trim().toLowerCase();
		String fileMimeType = onlineForm.getInputFileType();

		System.out.println("File name = " + fileName);
		System.out.println("File type = " + fileType);
		System.out.println("File size = " + onlineForm.getInputFile().length);
		System.out.println("File Mime Type = " + fileMimeType);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(fileMimeType));

		// If csv file, change to download. browser cannot open csv directly.
		if (fileType.equals("csv") || fileType.equals("xlsx"))
			headers.setContentDispositionFormData("attachment", onlineForm.getInputFileName());

		return new ResponseEntity<byte[]>(onlineForm.getInputFile(), headers, HttpStatus.OK);
	}

	@RequestMapping("/loadStaticQueryReport")
	public String loadStaticQueryReport(@ModelAttribute("onlineForm") OnlineForm onlineForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClassPathResource classPathResource = new ClassPathResource("StaticQueryReport.txt");
		InputStream input = classPathResource.getInputStream();
		byte[] reportTemplate = input.readAllBytes();
		input.close();
		String reportTemplateStr = new String(reportTemplate, StandardCharsets.UTF_8);
		
		onlineForm.setStaticQueryReport(new ObjectMapper().readValue(reportTemplateStr, StaticQueryReport.class));
		return "staticQueryReport";
	}
}
