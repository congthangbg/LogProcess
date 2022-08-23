package com.vn.service;

import org.springframework.stereotype.Service;

import com.vn.common.ResponseObject;

@Service
public interface LogService {

	ResponseObject findByIp(String ip);
	
}
