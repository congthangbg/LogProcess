package com.vn.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogJsonCDetails {
	@JsonProperty("PID")
	private String pid;
	@JsonProperty("ProcessName")
	private String processName;
	@JsonProperty("StartTime")
	private String startTime;
	@JsonProperty("ExecutablePath")
	private String executablePath;
}
