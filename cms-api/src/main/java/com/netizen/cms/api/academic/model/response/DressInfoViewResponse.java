package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DressInfoViewResponse {
	
	private Long dressId;
	private String dressDetails;
	private Integer dressSerial;
	private String classRange;
	private String gender;	
	
	private String fileName;
	private byte[] fileContent;
}
