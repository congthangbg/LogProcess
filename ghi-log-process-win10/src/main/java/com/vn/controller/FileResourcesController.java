package com.vn.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vn.common.sendData;

@CrossOrigin("*")
@RequestMapping("/api/v1")
@RestController
public class FileResourcesController {
	
	//comment 12
	
	static final String URL_UPLOAD_FILE = "http://localhost:8080/api/v1/logEncode";

	  private org.slf4j.Logger logger = LoggerFactory.getLogger(FileResourcesController.class);
	  
	@RequestMapping(value = "/downloadLog", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download2(HttpServletRequest request) throws IOException {
		HttpHeaders responseHeader = new HttpHeaders();

		String pattern = "yyyy_MM_dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		
		try {
			File file = ResourceUtils.getFile("classpath:logs/" + date + ".json");
			byte[] data = FileUtils.readFileToByteArray(file);

			String base = Base64.getEncoder().encodeToString(data);
			System.out.println(base.substring(0, 100));
			// Set mimeType trả về
			responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// Thiết lập thông tin trả về
			responseHeader.set("Content-disposition", "attachment; filename=" + file.getName());
			responseHeader.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
			return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/logEncode", method = RequestMethod.GET)
	public String download3ư(HttpServletRequest request) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		
		ObjectMapper mapper = new ObjectMapper();
		// Tạo Object Node
		ObjectNode node = mapper.createObjectNode();
		
		String pattern = "yyyy_MM_dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		try {
			File file = ResourceUtils.getFile("classpath:log/" + date + ".json");
			byte[] data = FileUtils.readFileToByteArray(file);

			String base = Base64.getEncoder().encodeToString(data);
			String baseDecode = new String(Base64.getDecoder().decode(base));
			
			node.put("ip",InetAddress.getLocalHost().getHostAddress());
			node.put("json",baseDecode);
			
			String json2 = mapper.writeValueAsString(node);
			System.out.println(json2.substring(0, 100));
			
			return json2;
	
		} catch (Error ex) {
			throw new RuntimeErrorException(ex);
		}
	}

	@GetMapping("/fileLog")
	public File fileOut() throws URISyntaxException, FileNotFoundException, IOException {

		String fileName = "log/2021_11_10.json";

		System.out.println("getResourceAsStream : " + fileName);
		InputStream is = getFileFromResourceAsStream(fileName);
		printInputStream(is);

		System.out.println("\ngetResource : " + fileName);
		File file = getFileFromResource(fileName);

		return file;
	}

	private InputStream getFileFromResourceAsStream(String fileName) {
		// The class loader that loaded the class
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		// the stream holding the file content
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}

	}

	private File getFileFromResource(String fileName) throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {

			// failed if files have whitespaces or special characters
			// return new File(resource.getFile());

			return new File(resource.toURI());
		}

	}

	// print input stream
	private static void printInputStream(InputStream is) {

		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// print a file
	private static void printFile(File file) {

		List<String> lines;
		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
//	            lines.forEach(e-> System.out.println(e.substring(0,100)));
//	            String encodedString = Base64.getEncoder().encodeToString(lines.getBytes());
//	            System.out.println(encodedString);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean sendData(String data) throws NoSuchAlgorithmException, KeyManagementException {

		String ApiUrl = "";
		try {
			ApiUrl = readFileToString("api.txt");
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(sendData.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (ApiUrl.trim().equals("")) {
			return false;
		} else {
			ApiUrl = ApiUrl.trim();
		}
		System.out.println(ApiUrl);

		// Kiểm tra kết nối
		boolean testURL = false;
		try {
			URL url = new URL(ApiUrl);
			URLConnection connection = url.openConnection();
			connection.connect();
			testURL = true;
			System.out.println("Connected: " + ApiUrl);
		} catch (MalformedURLException e) {
			System.out.println("Not connected: " + ApiUrl);
			testURL = false;
		} catch (IOException e) {
			System.out.println("Not connected: " + ApiUrl);
			testURL = false;
		}

		if (testURL == false) {
			return false;
		}
		System.out.println("Chuyen du lieu len server (neu co):\n" + data);

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(ApiUrl);

			StringEntity input = new StringEntity(data);
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = (HttpResponse) httpClient.execute(postRequest);

			if (((org.apache.http.HttpResponse) response).getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ ((org.apache.http.HttpResponse) response).getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader((((HttpEntityEnclosingRequestBase) response).getEntity().getContent())));

			String output;

			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	static public String readFileToString(String filename) throws FileNotFoundException, IOException {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}
}
