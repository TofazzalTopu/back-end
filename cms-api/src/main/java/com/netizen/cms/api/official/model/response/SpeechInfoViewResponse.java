package com.netizen.cms.api.official.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeechInfoViewResponse {
	private Long speechId;
	
	private String speechDetails;
	
	private String speakerImgName;
	
	private int speechSerial;
	
	private int speechStatus;
	
	private Date speechDate;
	
	private String speakerName;
	
	private String speakerDesignation;
	
	private String speakerEmail;
	
	private String speakerMobile;
	
	private String speakerFacebookLinke;
	
	private String speakerTwitterLinke;
	
	private String speakerLinkedinLinke;
	
	private Integer welcomeSpeechStatus;
	
	private String fileName;
	
	private byte[] fileContent;
	

}
