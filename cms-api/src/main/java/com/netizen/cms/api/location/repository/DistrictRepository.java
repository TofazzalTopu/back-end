package com.netizen.cms.api.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netizen.cms.api.location.model.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long>{

	public List<District> findByDivision_DivisionId(Integer divisionId);
	
	public District findByDistrictId(Integer districtId);
	
}
