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
public class ApplicantPersonalViewResponse {

	private Long applicantId;

	private String registrationId;

	private String applicantName;

	private Integer rollNo;

	private String mobileNo;

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

	private Date applicationDate;

	private Date statusUpdateDate;

	private Integer applicantStatus;

	private Integer applicantFeeStatus;

	private String quota;

	private String fileName;

	private byte[] fileContent;

	private Integer academicYear;

	private String clasName;

	private String groupName;

	private Date applicationStartDate;

	private Date applicationEndDate;

	private double applicationFee;
	
	private double serviceCharge;
	
	private double totalFee;

	private int admissionExamStatus;

	private int autoApproveStatus;

	private Date admissionExamDate;

	private Date admissionExamTime;

	private String admissionExamInstruction;

	private String examCenterName;

	private Integer districtId;

	private String districtName;

	private Integer divisionId;

	private String divisionName;
}
