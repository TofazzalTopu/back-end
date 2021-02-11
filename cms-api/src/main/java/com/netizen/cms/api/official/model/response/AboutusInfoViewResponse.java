package com.netizen.cms.api.official.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutusInfoViewResponse {

	private Long aboutusId;

	private String aboutusType;	
	
	private String aboutusDetails;	
	
	private String aboutusNote;
	
	private String fileName;
	
	private byte[] fileContent;
}
