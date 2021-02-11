package com.netizen.cms.api.location.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.location.model.entity.District;
import com.netizen.cms.api.location.model.entity.Division;
import com.netizen.cms.api.location.model.response.DistrictResponse;
import com.netizen.cms.api.location.model.response.DivisionResponse;
import com.netizen.cms.api.location.repository.DistrictRepository;
import com.netizen.cms.api.location.repository.DivisionRepository;





@Service
public class LocationService {
	
	@Autowired
	public DivisionRepository divisionRepository;
	
	@Autowired
	public DistrictRepository districtRepository;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse divisionList() {
		ItemResponse itemResponse=new ItemResponse();
		List<Division> divisions=divisionRepository.findAll();
		List<DivisionResponse> divisionResponses=new ArrayList<>();
		for(Division d:divisions) {
			DivisionResponse divisionResponse=new DivisionResponse();
			divisionResponse.setDivisionId(d.getDivisionId());
			divisionResponse.setDivisionName(d.getDivisionName());
			divisionResponses.add(divisionResponse);
		}
		itemResponse.setItem(divisionResponses);
		itemResponse.setMessageType(1);
		itemResponse.setMessage("ok");
		return itemResponse;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse districtListByDivisionId(Integer divisionId) {
		ItemResponse itemResponse=new ItemResponse();
		List<District> districts=districtRepository.findByDivision_DivisionId(divisionId);
		List<DistrictResponse> districtResponses=new ArrayList<>();
		for(District d:districts) {
			DistrictResponse districtResponse=new DistrictResponse();
			districtResponse.setDistrictId(d.getDistrictId());
			districtResponse.setDistrictName(d.getDistrictName());
			districtResponse.setDivisionId(d.getDivision().getDivisionId());
			districtResponse.setDivisionName(d.getDivision().getDivisionName());
			districtResponses.add(districtResponse);
			
		}
		itemResponse.setItem(districtResponses);
		itemResponse.setMessageType(1);
		itemResponse.setMessage("ok");
		return itemResponse;
	}

}
