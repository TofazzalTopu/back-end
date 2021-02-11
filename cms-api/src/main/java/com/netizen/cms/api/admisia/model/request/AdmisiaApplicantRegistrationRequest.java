package com.netizen.cms.api.admisia.model.request;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaApplicantRegistrationRequest {

	@NotNull
	private Long  cmsId;
	
	@NotNull
	private Long admisiaClassConfigId;
	
	@NotNull
	private Integer districtId;
	
	@NotNull
	private Integer academicYear;
	
	@NotBlank
	private String applicantName;

	@NotBlank
	private String mobileNo;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	private String religion;
	
	private Date dob;
	
	private String birthCertificateNo;
	
	@NotBlank
	private String fatherName;
	
	private String fatherNidNo;
	
	private String fatherOccupation;
	
	@NotBlank
	private String motherName;

	private String motherNidNo;
	
	private String motherOccupation;
	
	private String addressDetails;
	
	private String quota;
	
	private String fileName;
	private byte[] fileContent;
	private boolean fileSave;
	
	//private  boolean additionalInfoStatus;
	
	public List<AdmisiaApplicantRegistrationHelper> additionalInfos;
	
	
	
	
	
	
}
