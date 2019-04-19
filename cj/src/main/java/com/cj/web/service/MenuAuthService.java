package com.cj.web.service;

import org.springframework.stereotype.Service;

import com.cj.web.repository.MenuAuthRepository;

@Service
public class MenuAuthService {
	private MenuAuthRepository menuAuthRepository;
	
	public MenuAuthService(MenuAuthRepository menuAuthRepository) {
		this.menuAuthRepository = menuAuthRepository;
	}
	
	
}
