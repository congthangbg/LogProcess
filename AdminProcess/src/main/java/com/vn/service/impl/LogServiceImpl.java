package com.vn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.common.MapperUtils;
import com.vn.common.MessageConstant;
import com.vn.common.ResponseObject;
import com.vn.dto.LoglocalEntityResponse;
import com.vn.entity.LoglocalEntity;
import com.vn.repository.DataLogJsonRepository;
import com.vn.service.LogService;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	DataLogJsonRepository dataLogJsonRepository;

	@Override
	public ResponseObject findByIp(String ip) {
		ResponseObject res =new ResponseObject();
		try {
			List<LoglocalEntity> lst = dataLogJsonRepository.findByIp(ip);
			List<LoglocalEntityResponse> response = MapperUtils.mapAll(lst, LoglocalEntityResponse.class);
			res.setData(response);
			res.setMessage(MessageConstant.MSG_OK);
			res.setMessageCode(MessageConstant.MSG_OK_CODE1);
		} catch (Exception e) {
			res.setMessage("Error :"+e.getMessage());
		}
		return res;
	}
	
	
}
