package com.netizen.cms.api.official.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="aboutus_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id", "aboutus_type"}))
public class AboutusInfo implements Serializable{
	
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="aboutus_id")
	private Long aboutusId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name="aboutus_type", nullable = false)
	private String aboutusType;	

	@Column(name="aboutus_details", nullable = false)
	private String aboutusDetails;
	
	@Column(name="aboutus_note")
	private String aboutusNote;
	
	@Column(name="image_name")
	private String imageName;
}
