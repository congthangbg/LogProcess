package com.vn.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.vn.common.ResponseObject;
import com.vn.entity.DataLogJson;
import com.vn.entity.LogJsonCRequest;
import com.vn.entity.LogJsonCEntity;
import com.vn.entity.LoglocalEntity;
import com.vn.repository.DataLogJsonRepository;
import com.vn.repository.LogJsonCRepository;
import com.vn.service.LogService;

@CrossOrigin("*")
@RequestMapping("/api/v1/")
@RestController
public class GetBaseLog {
	@Autowired
	DataLogJsonRepository dataLogJsonRepository;
	
	@Autowired
	LogJsonCRepository logJsonCRepository;

	@Autowired
	LogService logService;

	@GetMapping("/getLogbyId")
	public ResponseEntity<InputStreamResource> getLogById(@RequestParam String ip) throws IOException {
		ResponseObject ob = logService.findByIp(ip);
		HttpHeaders responseHeader = new HttpHeaders();
		String pattern = "yyyy_MM_dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		try {
			// Set mimeType trả về
			responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// Thiết lập thông tin trả về
			responseHeader.set("Content-disposition", "attachment; filename=" + date + ip);
//			responseHeader.setContentLength(ob.getData());
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream((byte[]) ob.getData()));
			InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
			return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/getLog")
	public void getBase(@RequestBody String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		DataLogJson dataLogJson = objectMapper.readValue(json, DataLogJson.class);

		LoglocalEntity loglocalEntity = new LoglocalEntity();

		loglocalEntity.setIpTinh(dataLogJson.getIpTinh());
		loglocalEntity.setUserLocal(dataLogJson.getUserLocal());
		loglocalEntity.setCreateDate(Timestamp.from(Instant.now()));
		loglocalEntity.setIp(dataLogJson.getIp());
		loglocalEntity.setJson(dataLogJson.getJson().getBytes());
		loglocalEntity.setModifyDate(Timestamp.from(Instant.now()));
		loglocalEntity.setStatus(1);

		dataLogJsonRepository.save(loglocalEntity);

		System.out.println("Save success");

	}
	
	@PostMapping("/getLogC")
	public void getLogC(@RequestBody String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		LogJsonCRequest logJsonC= objectMapper.readValue(json, LogJsonCRequest.class);
		
		LogJsonCEntity loglocalEntity = new LogJsonCEntity();
		loglocalEntity.setDomainName(logJsonC.getDomainName());
		loglocalEntity.setHostName(logJsonC.getHostName());
		loglocalEntity.setIPv4Dynamic(logJsonC.getIpv4Dynamic());
		loglocalEntity.setIPv4Static(logJsonC.getIpv4Static());
		loglocalEntity.setMACAddress(logJsonC.getMacAddress());
		loglocalEntity.setNumberProcessUser(logJsonC.getNumberProcessUser());
		loglocalEntity.setTotalProcess(logJsonC.getTotalProcess());
		loglocalEntity.setIsBkav(logJsonC.getIsBkav());
		loglocalEntity.setIsConnectInternet(logJsonC.getIsConnectInternet());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(logJsonC.getProcessDetails());
		
		loglocalEntity.setProcessDetails(jsonString.getBytes());
		loglocalEntity.setUserName(logJsonC.getUserName());
		

		logJsonCRepository.save(loglocalEntity);

		System.out.println("Save log server success");

	}


}
