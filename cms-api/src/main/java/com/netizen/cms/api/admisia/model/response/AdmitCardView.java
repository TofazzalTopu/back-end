package com.netizen.cms.api.admisia.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmitCardView {
	
	private String registrationNo;
	private Integer rollNo;
	private String applicantName;
	private String gender;
	private String religion;
	private String fatherName;
	private String motherName;
	private String mobileNo;
	private String academicYear;
	private String className;
	private String groupName;
	private String examDate;
	private String examTime;
	private String examCenter;
	private String photoName;
	
	

}
