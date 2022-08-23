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
@Table(name = "LOG_LOCAL")
public class LoglocalEntity implements Serializable{
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id ;
	
	@Column(name = "JSON")
	@Lob
	private byte[] json;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "IP_TINH")
	private String ipTinh;
	
	@Column(name = "USER_LOCAL")
	private String userLocal;
	
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	
	@Column(name = "MODIFY_DATE")
	private Timestamp modifyDate ;
	
	@Column(name = "CREATOR")
	private String creator;
	
	@Column(name = "REPAIR")
	private String repair;
	
	@Column(name = "STATUS")
	private Integer status;
	
}
