package com.netizen.cms.api.academic.model.entity;

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
@Table(name="dress_info", uniqueConstraints = @UniqueConstraint(columnNames = {"class_range", "gender", "cms_id"}))
public class DressInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dress_id")
	private Long dressId;
	
	@Column(name="dress_details", nullable = false)
	private String dressDetails;
	
	@Column(name="dress_img_name")
	private String dressImageName;
	
	@Column(name="dress_serial", columnDefinition = "Integer default 0")
	private Integer dressSerial;
	
	@Column(name="class_range", nullable = false)
	private String classRange;
	
	@Column(name = "gender", nullable = false)
	private String gender;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;	

}
