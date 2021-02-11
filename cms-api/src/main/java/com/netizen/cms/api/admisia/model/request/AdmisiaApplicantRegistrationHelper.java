package com.netizen.cms.api.admisia.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaApplicantRegistrationHelper {

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
	
	
}
