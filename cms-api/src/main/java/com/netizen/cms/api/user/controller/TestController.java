package com.netizen.cms.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.user.service.UserService;

@RestController
@RequestMapping(value = "/public")
public class TestController {
	
	@Autowired
    public UserService userService;
	
	 @RequestMapping(value = "/user/list",method = RequestMethod.GET)
	    public ResponseEntity<ItemResponse> userList(){
	        ItemResponse itemResponse=userService.userList();
	        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	        
	    }

}
