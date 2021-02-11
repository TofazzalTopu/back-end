package com.netizen.cms.api.admisia.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="admisia_applicant_prevexam_info")
public class AdmisiaApplicantPrevexamInfo implements Serializable{
	
	private static final long serialVersionUID = -7726893041042469950L;

	@Id
	@Column(name="prevexam_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long previousExamId;
	
	@ManyToOne
	@JoinColumn(name="applicant_id", nullable = false)
	private AdmisiaApplicantInfo admisiaApplicantInfo;
	
	@ManyToOne
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name="institute_type")
	private String instituteType;
		
	@Column(name="institute_name", nullable = false)
	private String instituteName;
	
	@Column(name="board_name")
	private String boardName;
	
	@Column(name = "exam_name", nullable = false)
	private String examName;
	
	@Column(name="roll_no", nullable = false)
	private Integer rollNo;
	
	@Column(name="registration_no")
	private String registrationNo;
	
	@Column(name="exam_grade", nullable = false)
	private String examGrade;
	
	@Column(name="exam_gpa", nullable = false)
	private Double examGpa;
	
	@Column(name="passing_year", nullable = false)
	private Integer passingYear;
	
	@Column(name="class_name", nullable = false)
	private String className;
	
}
