package com.cj.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cj.web.domain.User;
import com.cj.web.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	} 
	
	public Page<User> findByList(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	/**
	 * search for like user name 
	 */
	public Page<User> findByNameLike(String sch_value, Pageable pageable){
		return userRepository.findByNameLike("%"+sch_value+"%", pageable);
	}
	
	public User findById(Long id) {		
		return userRepository.findOne(id);
	}
	
	public User findByUserId(String userId) {						// Long 타입의 일련번호가 아닌 특정 컬럼 유형검색인 경우 repository 에 해당 메소드 선언
		return userRepository.findByUserId(userId);		
	}
	
	public void insertUser(User user) {
		userRepository.save(user); 
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
}
