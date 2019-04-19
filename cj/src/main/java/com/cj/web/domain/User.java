package com.cj.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cj.web.domain.enums.UserClass;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User implements Serializable{
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="PASSWD")
	private String passwd;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="USER_CLASS")
	@Enumerated(EnumType.STRING)
	private UserClass userClass;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATETIME")
	private java.util.Date createDatetime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_DATETIME")
	private java.util.Date updateDatetime;
	
	@Builder
	public User(String userId, String passwd, String name, String email, UserClass userClass, java.util.Date createDatetime, java.util.Date updateDatetime) {
		this.userId = userId;
		this.passwd = passwd;
		this.name = name;
		this.email = email;
		this.userClass = userClass;
		this.createDatetime = createDatetime;
		this.updateDatetime = updateDatetime;
	}
	
	public void updateDInfo(User updUser) {
		//this.passwd = updUser.getPasswd();	// 비밀번호는 따로 수정하도록 처리 필요
		this.name = updUser.getName();
		this.email = updUser.getEmail();
		this.userClass = updUser.getUserClass();
		this.updateDatetime = updateDatetime;
	}
	
	public boolean matchPasswd(String newPasswd) {
		if(this.passwd.equals(newPasswd)) return true;
		else return false;
	}
}
