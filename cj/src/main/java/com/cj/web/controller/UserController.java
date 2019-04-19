package com.cj.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.net.URLCodec;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cj.web.domain.User;
import com.cj.web.service.UserService;
import com.cj.web.util.AES256Util;
import com.cj.web.util.PageUtil;
import com.cj.web.util.SHA256Util;
import com.cj.web.util.UserSessionUtil;

@RequestMapping("users") 
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	private static Logger logger = LogManager.getLogger(UserController.class);
	
	@Value("${msgsi.aeskey}")
	private String aesKey;
	URLCodec codec = new URLCodec();
	
	@GetMapping("list")
	public String getUserList(@PageableDefault(sort= {"id"}, direction = Direction.DESC, size=10) Pageable
			pageable,HttpSession session, @RequestParam Map<String, Object> map, Model model) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		
		Page<User> users = userService.findByList(pageable);
		PageUtil pageUtil = PageUtil.getInstance();
		
		int total = Integer.parseInt(""+users.getTotalElements());
		int totalPage = users.getTotalPages();
		//String curPage = (String)map.get("page");
		String curPage = ""+pageable.getPageNumber();
		System.out.println("curPage"+curPage);
		
		if(curPage == null || curPage.equals("") || curPage.equals("0")) curPage = "1";			
		try {
			AES256Util aes = new AES256Util(aesKey);			
			// 개인정보 복호화 처리 : AES256
			for(int i=0;i<users.getContent().size();i++) {
				users.getContent().get(i).setEmail(aes.aesDecode(codec.decode(users.getContent().get(i).getEmail())) );	// email
			}			
		}catch(Exception e) {
			logger.error("/users/list (getUserList)"+e.getMessage());
		}
		
		model.addAttribute("users", users);
		model.addAttribute("pagelist", pageUtil.getPageList("goPage", total, totalPage, pageable.getPageSize(), curPage));
		return "/users/list";
	}
	
	@PostMapping("list") 
	public String getPostUserList(@PageableDefault(sort= {"id"}, direction = Direction.DESC, size=10) Pageable
			pageable,HttpSession session, @RequestParam Map<String, Object> map, Model model) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		
		String sch_value = (String)map.get("sch_value");	// 검색어
		Page<User> users;
		if(sch_value != null) users = userService.findByNameLike(sch_value, pageable);
		else users = userService.findByList(pageable);
		
		PageUtil pageUtil = PageUtil.getInstance();
		
		// 페이지 하단 페이지 리스트 계산
		int total = Integer.parseInt(""+users.getTotalElements());
		int totalPage = users.getTotalPages();
		String curPage = (String)map.get("page");
		if(curPage == null || curPage.equals("") || curPage.equals("0")) curPage = "1";
					
		try {
			AES256Util aes = new AES256Util(aesKey);			
			// 개인정보 복호화 처리 : AES256
			for(int i=0;i<users.getContent().size();i++) {
				users.getContent().get(i).setEmail(aes.aesDecode(codec.decode(users.getContent().get(i).getEmail())) );	// email
			}			
		}catch(Exception e) {
			logger.error("/users/list (getUserList)"+e.getMessage());
		}
		
		model.addAttribute("query", map);
		model.addAttribute("users", users);
		model.addAttribute("pagelist", pageUtil.getPageList("goPage", total, totalPage, pageable.getPageSize(), curPage));
		return "/users/list";
	}
	
	@GetMapping("form")
	public String adminUserCreateForm(HttpSession session, Model model){
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		// 관리자만 접근 가능
		if(!UserSessionUtil.isAdmin(session)) return "redirect:/users/list";

		
		model.addAttribute("user", new User());
		
		return "/users/form";
	}
	
	@GetMapping("{id}/edit")
	//public String adminUpdateForm(HttpSession session,@RequestParam(value="id", defaultValue="0") Long id, Model model) {
	public String adminUpdateForm(@PathVariable Long id, HttpSession session,Model model) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		// 관리자만 접근 가능
		if(!UserSessionUtil.isAdmin(session)) return "redirect:/users/list";
		
		User user = userService.findById(id);		
		
		try {
			AES256Util aes = new AES256Util(aesKey);
			user.setEmail(aes.aesDecode(codec.decode(user.getEmail())));		// 개인정보 복호화 : AES256			
		}catch(Exception e) {
			logger.error("/users/edit (adminUpdateForm)"+e.getMessage());
		}
		
		model.addAttribute("user", user);
		
		return "/users/updateForm";		
	}
	
	
	@PostMapping("create")
	public String adminUserCreate(HttpSession session, User user) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		// 관리자만 접근 가능
		if(!UserSessionUtil.isAdmin(session)) return "redirect:/users/list";		
		
		try {
			AES256Util aes = new AES256Util(aesKey);
			
			user.setPasswd(SHA256Util.getEncrypt(user.getPasswd(),""));			// 비밀번호 암호화 : SHA256
			user.setEmail(codec.encode(aes.aesEncode(user.getEmail())));		// 개인정보 암호화 : AES256
			
			user.setCreateDatetime(new java.util.Date());
			user.setUpdateDatetime(new java.util.Date());
			System.out.println(user.toString());
			
			userService.insertUser(user);		
			
		}catch(Exception e) {
			logger.error("/users/create (adminUserCreate)"+e.getMessage());
		}
		
		return "redirect:/users/list";
	}
	
	//@PostMapping("update")
	@PutMapping("update")
	public String adminUserUpdate(HttpSession session, User user) {	
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		// 관리자만 접근 가능
		if(!UserSessionUtil.isAdmin(session)) return "redirect:/users/list";
		
		User ori_user = userService.findById(user.getId()); 
		
		try {
			AES256Util aes = new AES256Util(aesKey);			
			user.setPasswd(SHA256Util.getEncrypt(user.getPasswd(),""));			// 비밀번호 암호화 : SHA256
			user.setEmail(codec.encode(aes.aesEncode(user.getEmail())));		// 개인정보 암호화 : AES256
			user.setUpdateDatetime(new java.util.Date());
			
			ori_user.updateDInfo(user);
			userService.updateUser(ori_user);						
		}catch(Exception e) {
			logger.error("/users/update (adminUserUpdate)"+e.getMessage());
		}		
				
		return "redirect:/users/list";
	}
	
	@PostMapping("login")
	public String userLoin(String userId, String passwd, HttpSession session, Model model) {
		String code = "200";
		User user = userService.findByUserId(userId);
		if(user == null) {			
			code = "404";
			model.addAttribute("code", code);
			model.addAttribute("message", "로그인에 실패 했습니다.");
			
			logger.info(code);
			return "/index";
		}
				
		String encriptPasswd = SHA256Util.getEncrypt(passwd,"");			// 비밀번호 복호화 : SHA256

		if(code.equals("200")) {
			if(!user.matchPasswd(encriptPasswd)) {
				code = "403";
				model.addAttribute("code", code);
				model.addAttribute("message", "로그인에 실패 했습니다.");				
				return "/index";				
			}			
		}
				
		model.addAttribute("code", code);
		session.setAttribute("sessUser", user);
		
		return "redirect:/users/list";
	}
	
	@GetMapping("logout")
	public String userLogOut(HttpSession session, Model model) {		
		session.removeAttribute("sessUser");
		
		return "redirect:/";
	}
	
	
	@GetMapping("userUpdateForm")
	public String userUpdateForm(HttpSession session, Model model) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		
		User sessUser = UserSessionUtil.getUserFromSession(session);
		User user = userService.findById(sessUser.getId());
		
		try {
			AES256Util aes = new AES256Util(aesKey);
			user.setEmail(aes.aesDecode(codec.decode(user.getEmail())));		// 개인정보 복호화 : AES256			
		}catch(Exception e) {
			logger.error("/users/edit (adminUpdateForm)"+e.getMessage());
		}		
		model.addAttribute("user", user);
		
		return "/users/userUpdateForm";		
	}
	
	@PutMapping("updateUser")
	public String userUpdate(HttpSession session, User user) {
		// 로그인 체크
		if(!UserSessionUtil.isLoginUser(session)) return "redirect:/";
		
		User ori_user = userService.findById(user.getId());
		
		User sessUser = UserSessionUtil.getUserFromSession(session);
		if(!sessUser.getUserId().equals(ori_user.getUserId())) {	
			return "redirect:/";
		}
		
		ori_user.updateDInfo(user);
		userService.updateUser(ori_user);
				
		return "redirect:/";
	}	
	
}
