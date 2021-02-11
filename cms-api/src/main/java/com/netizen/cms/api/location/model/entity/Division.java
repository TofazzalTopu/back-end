package com.netizen.cms.api.location.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location_division")
public class Division implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1411372019782820038L;

	@Id
	@Column(name="division_id")
	private Integer divisionId;
	
	@Column(name="division_name",unique = true)
	private String divisionName;

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
}
