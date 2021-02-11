package com.netizen.cms.api.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.location.service.LocationService;



@Controller
@RequestMapping(value = "/public/location/info")
public class LocationPublicController {
	
	@Autowired
	public LocationService locationService;
	
	
	@GetMapping(value = "/division-list")
	public ResponseEntity<ItemResponse> findDivisionList(){
		ItemResponse itemResponse=locationService.divisionList();
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	@GetMapping(value = "/district-list/by/division-id")
	public ResponseEntity<ItemResponse> findDistrictByDivisionId(@RequestParam Integer divisionId){
		ItemResponse itemResponse=locationService.districtListByDivisionId(divisionId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
