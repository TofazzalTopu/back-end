package com.netizen.cms.api.admisia.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.location.model.entity.District;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="admisia_applicant_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id","class_config_id","academic_year","applicant_name","mobile_no"}))
public class AdmisiaApplicantInfo implements Serializable{
		
	private static final long serialVersionUID = 3433779899995310138L;

	@Id
	@Column(name="applicant_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicantId;
	
	@Column(name="registration_id", nullable = false, unique = true)
	private String registrationId;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id",nullable = false)
	private CmsInfo cmsInfo;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="class_config_id", nullable = false)
	private AdmisiaClassConfig admisiaClassConfig;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private District district;
	
	@Column(name="academic_year", nullable = false)
	private Integer academicYear;
	
	@Column(name="applicant_name", nullable = false)
	private String applicantName;
	
	@Column(name="roll_no", nullable = false)
	private Integer rollNo;
	
	@Column(name="mobile_no", nullable = false)
	private String mobileNo;
	
	@Column(name="gender", nullable = false)
	private String gender;
	
	@Column(name="religion", nullable = false)
	private String religion;
	
	@Column(name="dob", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="birth_certificate_no", nullable = false)
	private String birthCertificateNo;
	
	@Column(name="father_name", nullable = false)
	private String fatherName;
	
	@Column(name="father_nid_no", nullable = false)
	private String fatherNidNo;
	
	@Column(name="father_occupation", nullable = false)	
	private String fatherOccupation;
	
	@Column(name="mother_name", nullable = false)
	private String motherName;
	
	@Column(name="motherr_nid_no", nullable = false)
	private String motherNidNo;
	
	@Column(name="mother_occupation", nullable = false)
	private String motherOccupation;
	
	@Column(name="address_details", nullable = false)
	private String addressDetails;
	
	@Column(name="applicant_photo", nullable = false)
	private String applicantPhoto;
	
	@Column(name="application_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date applicationDate;
	
	@Column(name = "applicant_status", columnDefinition = "Integer default 0")
	private Integer applicantStatus;
	
	@Column(name="exam_marks", columnDefinition = "Double default 0")
	private Double examMarks;
	
	@Column(name="exam_result")
	private String examResult;
	
	@Column(name="applicant_fee_status", columnDefinition = "Integer default 0")
	private Integer applicantFeeStatus;
	
	@Column(name="quota", nullable = false)
	private String quota;
	
	@Column(name="status_update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date statusUpdateDate;
	
	@Column(name="user_executed", nullable = false)
	private String userExecuted;
	
	
	@OneToMany(mappedBy = "admisiaApplicantInfo", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<AdmisiaApplicantPrevexamInfo> admisiaApplicantPrevexamInfos;
	
	

}
