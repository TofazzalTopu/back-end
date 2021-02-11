package com.netizen.cms.api.admisia.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantPreviousExamViewResponse {

	private Long previousExamId;

	private String instituteType;

	private String instituteName;

	private String boardName;

	private String examName;

	private Integer rollNo;

	private String registrationNo;

	private String examGrade;

	private Double examGpa;

	private Integer passingYear;

	private String className;
	
	private String applicantRegistrationId;

	private String applicantName;
	
	private String mobileNo;
	
	private String fatherName;

	private String motherName;
	
	private Integer applicantStatus;
}
