package com.netizen.cms.api.academic.model.request;

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
public class DressInfoSaveRequest {
	
	@NotBlank
	private String dressDetails;

//	@NotBlank
//	private String dressImageName;
	
	private Integer dressSerial;

	@NotBlank
	private String classRange;

	@NotBlank
	private String gender;	
	
	@NotNull
	private Long cmsId;
	
	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;

}
