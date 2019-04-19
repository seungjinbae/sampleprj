package com.cj.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cj.web.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserId(String userId);
	
	Page<User> findByNameLike(String name, Pageable pageable);	
}
