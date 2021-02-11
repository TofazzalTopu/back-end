package com.netizen.cms.api.cmsinfo.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cms_info")
public class CmsInfo implements Serializable{

	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cms_id")
	private Long cmsId;	
	
	@Column(name="custom_cms_id",unique = true, nullable = false)
	private Long customCmsId;
	
	@Column(name="store_id",unique = true)
	private String storeId;
	
	@Column(name="store_passwd")
	private String storePasswd;
	
	@Column(name="user_role_assign_id",unique = true, nullable = false)
	private Long userRoleAssignId;	
	
	
	@Column(name="url_name", unique = true, nullable = false)
	private String urlName;
	
	@Column(name="url_status", columnDefinition = "int default 0")
	private Integer urlStatus;
	
	@Column(name = "cms_create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date cmsCreateDate;
	
	@Column(name="application_type", nullable = false)
	private String applicationType;
	
	@Column(name = "application_package", nullable = false)
	private String applicationPackage;
	
	@Column(name = "application_theme", nullable = false)
	private String applicationTheme;	
	
	@Column(name="logo_name", nullable = false)
	private String logoName;
	
	@Column(name="default_language", nullable = false)
	private String defaultLanguage;
	
	@Column(name="institute_name", nullable = false)
	private String instituteName;
	
	@Column(name="institute_address", nullable = false)
	private String instituteAddress;
	
	@Column(name="institute_contact", nullable = false)
	private String instituteContact;
	
	@Column(name="institute_email", nullable = false)
	private String instituteEmail;
	
	@Column(name="mapped_status", columnDefinition = "int default 0")
	private int mappedStatus;
	
	@Column(name="visitor_count", columnDefinition = "int default 0")
	private int visitorCount;
	
	
}
