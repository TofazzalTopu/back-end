/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netizen.cms.api.user.service;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.repository.CmsInfoRepository;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.UserInfoUtils;
//import com.netizen.cms.api.configuration.BasicSecurityConfig;
import com.netizen.cms.api.user.model.dto.UserDto;
import com.netizen.cms.api.user.model.entity.UserRoles;
import com.netizen.cms.api.user.model.entity.Users;
import com.netizen.cms.api.user.model.request.LoginRequest;
import com.netizen.cms.api.user.repository.UserRolesRepository;
import com.netizen.cms.api.user.repository.UsersRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author riad
 */
@Service
public class UserService {
    
    @Autowired
    public UsersRepository usersRepository;
    
    @Autowired
    public UserRolesRepository userRolesRepository;
    
    @Autowired
    private CmsInfoRepository cmsInfoRepository;
    
	/*
	 * @Transactional public BaseResponse createUser(UserDto userDto){
	 * 
	 * BaseResponse baseResponse=new BaseResponse(); Users users=new Users(); String
	 * encryptedPassword=UserInfoUtils.getHashPassword(userDto.getPassword());
	 * users.setEnabled(true); users.setPassword(encryptedPassword);
	 * users.setUsername(userDto.getUsername()); List<UserRoles> userRoles=new
	 * ArrayList<>(); UserRoles roles;
	 * 
	 * for(String r:userDto.getUserRoles()){ roles=new UserRoles();
	 * roles.setUsername(userDto.getUsername()); roles.setRoleName(r);
	 * userRoles.add(roles); }
	 * 
	 * usersRepository.save(users); userRolesRepository.saveAll(userRoles);
	 * 
	 * baseResponse.setMessage("User has been created succesfully"); return
	 * baseResponse;
	 * 
	 * }
	 */
    
    @Transactional
    public BaseResponse deleteUser(Long id){
       
        BaseResponse baseResponse=new BaseResponse();
        Optional<Users> users=usersRepository.findById(id);
 //       List<UserRoles> roleses=userRolesRepository.findByUsername(users.get().getUsername());
        
        //usersRepository.delete(users);
       // userRolesRepository.delete(roleses);
        
        baseResponse.setMessage("User has been deleted succesfully");
        return baseResponse;
        
    }
    
    public List<String> userroles(List<UserRoles> roles,String username){
       
        List<String> userroles=new ArrayList<>();
        for(UserRoles r:roles){
          if(r.getUsername().equals(username)){
           userroles.add(r.getRoleName());
          }  
        }
        
        return userroles; 
    }
    
    public ItemResponse userList(){
        ItemResponse itemResponse=new ItemResponse();
        List<Users> users=usersRepository.findAll();
        List<UserRoles> roles=userRolesRepository.findAll();
        
        List<UserDto> userDtos=new ArrayList<>();
        UserDto userDto;
        for(Users u:users){
         userDto=new UserDto();
         userDto.setUserId(u.getId());
			/*
			 * userDto.setUsername(u.getUsername()); userDto.setUserRoles(userroles(roles,
			 * u.getUsername()));
			 */
         userDtos.add(userDto);
        }
        
        itemResponse.setItem(userDtos);
        itemResponse.setMessage("OK");
        return itemResponse;
        
        
    }

	public Map<String, Object> login(LoginRequest loginRequest) {
				
		Map<String, Object> map = new HashMap<>();		
		map.put("messageType", 0);
				
	
		CmsInfo cmsInfo = cmsInfoRepository.findByUserRoleAssignIdAndUrlStatus(loginRequest.getRoleAssignId(), 1);	
		
		if(cmsInfo == null) {
			map.put("message", "No Active Cms Found");		
			return map;
		}		
		
		
	//	String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
	//	if( ! StringUtils.isEmpty(username)) {
			map.put("cmsId", cmsInfo.getCmsId());
		//	map.put("message", "Login Success");			
			map.put("messageType", 1);
			return map;
//		}
		
	//	map.put("message", "Username or Password is incorrect");
	
	//	return map;
	}
     
}
