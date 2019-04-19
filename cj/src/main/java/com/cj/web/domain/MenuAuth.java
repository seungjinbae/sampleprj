package com.cj.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cj.web.domain.enums.MenuCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="TBL_MENUAUTH")
@NoArgsConstructor
@Data
public class MenuAuth implements Serializable{
	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long idx;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")		// join 시에는 @Column 이 아닌 @JoinColumn을 사용하세요.
	private User user;
	
	@Column(name="MENU_CODE")
	private MenuCode menuCode;
	
	@Column(name="CREATE_DATETIME")
	private java.util.Date createDatetime;
}
