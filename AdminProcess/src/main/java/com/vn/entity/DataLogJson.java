package com.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataLogJson {
	private String ipTinh;
	private String ip;
	private String json;
	private String userLocal;
}
