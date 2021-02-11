package com.netizen.cms.api.others.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="alumonus_member_list")
public class AlumonusMemberList implements Serializable{

	
	private static final long serialVersionUID = -4804161050809627198L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="alumonus_member_id")
	private Long alumonusMemberId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name = "serial", columnDefinition = "Integer default 0")
	private Integer serial;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "designation", nullable = false)
	private String designation;
	
	@Column(name = "organization", nullable = false)
	private String organization;
	
	@Column(name = "batch", nullable = false)
	private String batch;
	
	@Column(name = "details", nullable = false)
	private String details;
	
	@Column(name = "contact_no", nullable = false)
	private String contactNo;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name="create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "create_by", nullable = false)
	private String createBy;
	
	@Column(name="modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	
}
