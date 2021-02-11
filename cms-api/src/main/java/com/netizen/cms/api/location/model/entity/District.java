package com.netizen.cms.api.location.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="location_district")
public class District implements Serializable{
	

	private static final long serialVersionUID = 5340237280963589988L;

	@Id
	@Column(name="district_id")
	private Integer districtId;
	
	@Column(name="district_name",unique = true)
	private String districtName;
	
	@ManyToOne
	@JoinColumn(name="division_id")
	private Division division;

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	

}
