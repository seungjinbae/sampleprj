package com.cj.web.util;

import javax.servlet.http.HttpSession;

import com.cj.web.domain.User;
import com.cj.web.domain.enums.UserClass;

public class UserSessionUtil {
	public static final String USER_SESSION_KEY = "sessUser";
	
	/** 로그인 체크
	 * @param sess
	 * @return
	 */
	public static boolean isLoginUser(HttpSession sess) {
		Object sessUser = sess.getAttribute(USER_SESSION_KEY);		
		if(sessUser == null) return false;
		return true;
	}
	
	/**
	 * 관리자 권한 체크
	 * @param sess
	 * @return
	 */
	public static boolean isAdmin(HttpSession sess) {		
		User sessUser = (User)sess.getAttribute(USER_SESSION_KEY);		
		if(sessUser == null) return false;
		else {
			if(sessUser.getUserClass() == UserClass.ADM) return true;
			else return false;
		}
	}	
	
	/**
	 * 세션 객체 반환
	 * @param sess
	 * @return
	 */
	public static User getUserFromSession(HttpSession sess) {
		if(!isLoginUser(sess)) {
			return null;
		}		
		return (User)sess.getAttribute(USER_SESSION_KEY);
	}
}
