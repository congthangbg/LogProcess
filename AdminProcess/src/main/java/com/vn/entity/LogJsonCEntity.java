package com.vn.entity;

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
@Entity
@Table(name = "LOG_LOCAL_C")
public class LogJsonCEntity implements Serializable{
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id ;
	
	@Column(name = "PROCESS_DETAILS")
	@Lob
	private byte[] ProcessDetails;
	
	@Column(name = "HOST_NAME")
	private String HostName;
	
	@Column(name = "DOMAIN_NAME")
	private String DomainName;
	
	@Column(name = "USER_NAME")
	private String UserName;
	
	@Column(name = "MAC_ADDRESS")
	private String MACAddress;
	
	@Column(name = "IPV4_DYNAMIC")
	private String IPv4Dynamic;
	
	@Column(name = "IPV4_STATIC")
	private String IPv4Static;
	
	@Column(name = "IS_BKAV")
	private String isBkav;
	
	@Column(name = "IS_CONNECT_INTERNET")
	private String isConnectInternet;
	
	@Column(name = "TOTAL_PROCESS")
	private Integer TotalProcess;
	
	@Column(name = "NUMBER_PROCESS_USER")
	private Integer NumberProcessUser;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate = Timestamp.from(Instant.now());
	
	@Column(name = "MODIFY_DATE")
	private Timestamp modifyDate  = Timestamp.from(Instant.now()); 
	
	@Column(name = "CREATOR")
	private String creator;
	
	@Column(name = "REPAIR")
	private String repair;
	
	@Column(name = "STATUS")
	private Integer status =1;
	
}
