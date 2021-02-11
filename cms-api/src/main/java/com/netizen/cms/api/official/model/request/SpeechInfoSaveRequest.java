package com.netizen.cms.api.official.model.request;

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
public class SpeechInfoSaveRequest {	
	
	private int speechSerial;
	
	@NotBlank
	private String speakerName;
	
	@NotBlank
	private String speakerDesignation;
	
	//@NotBlank
	private String speakerMobile;
	
	private String speakerEmail;
	
	private String speakerImgName;
	
	private String speakerFacebookLinke;
	
	private String speakerTwitterLinke;
	
	private String speakerLinkedinLinke;
	
	private Integer welcomeSpeechStatus;
	
	@NotBlank
	private String speechDetails;

	@NotNull
	private Long cmsId;
	
	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;
}
