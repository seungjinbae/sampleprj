package com.cj.web.domain.enums;

public enum UserClass {
	ADM("관리자"), USR("일반사용자");
	
	private String value;
	
	UserClass(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
