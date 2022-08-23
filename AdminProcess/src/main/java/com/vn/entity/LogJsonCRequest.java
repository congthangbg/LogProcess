package com.vn.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogJsonCRequest {
	private String hostName;
	private String domainName;
	private String userName;
	private String macAddress;
	private String ipv4Dynamic;
	private String ipv4Static;
	private String isBkav;
	private String isConnectInternet;
	private Integer totalProcess;
	private Integer numberProcessUser;
	private List<LogJsonCDetails> processDetails;
	
}
