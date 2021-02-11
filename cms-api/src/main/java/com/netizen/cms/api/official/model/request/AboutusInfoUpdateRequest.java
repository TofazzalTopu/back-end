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
public class AboutusInfoUpdateRequest {

	@NotNull
	private Long aboutusId;

	@NotBlank
	private String aboutusType;	
	
	@NotBlank
	private String aboutusDetails;	
	
	private String aboutusNote;
	
	@NotNull
	private Long cmsId;	
		
	private String fileName;
	
	private byte[] fileContent;
	
	private boolean fileSaveOrEditable;
}
