package com.cs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.config.DbContextHolder;
import com.cs.config.DbType;
import com.cs.domain.User;
import com.cs.service.UserService;
import com.cs.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class UserController {


	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/{dbtype}")
	public ResponseEntity<List<User>> getAllUsersByAdmin(Pageable pageable, @PathVariable("dbtype") String type) {
		try {
			if(type.equals(DbType.wshop.toString())){
				DbContextHolder.setDbType(DbType.wshop);
				final Page<User> page = userService.getAllManagedUsers(pageable);
				HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
				return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
			}else if(type.equals(DbType.wshop_common.toString())) {
				DbContextHolder.setDbType(DbType.wshop_common);
				final Page<User> page = userService.getAllManagedUsers(pageable);
				HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
				return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} finally {
			DbContextHolder.clearDbType();
		}
	}


}
