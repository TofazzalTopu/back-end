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
public class ApplicantAssesmentOrApprovalViewResponse {
	
	private Long applicantId;
	
	private Long classConfigId;	

	private String registrationId;

	private Integer academicYear;

	private String applicantName;

	private Integer rollNo;

	private String mobileNo;
	
	private String clasName;
	
	private String groupName;

	private String gender;

	private String religion;
	
	private Date dob;	

	private String birthCertificateNo;

	private String fatherName;

	private String fatherNidNo;

	private String fatherOccupation;

	private String motherName;

	private String motherNidNo;

	private String motherOccupation;

	private String addressDetails;
	
	private Date applicationStartDate;
	
	private Date applicationEndDate;

	private Date applicationDate;
	
	private Date statusUpdateDate;

	private Integer applicantStatus;

	private Double examMarks;

	private String examResult;

	private Integer applicantFeeStatus;

	private String quota;
	
	private Integer districtId;

	private String districtName;

	private Integer divisionId;

	private String divisionName;
	
	private String fileName;

	private byte[] fileContent;

}
