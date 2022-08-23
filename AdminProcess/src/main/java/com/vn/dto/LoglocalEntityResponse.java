package com.vn.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoglocalEntityResponse implements Serializable{
	
	private Long id ;
	
//	private byte[] json;
	
	private String ip;
	
	private Timestamp createDate;
	
//	private Timestamp modifyDate ;
//	
//	private String creator;
//	
//	private String repair;
//	
//	private Integer status;
	
}
