package com.cj.web.domain.enums;

public enum MenuCode {
	MEM01("사용자관리"), PUSH01("푸시 대시보드");
	
	private String value;
	
	MenuCode(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
