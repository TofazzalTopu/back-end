package com.netizen.cms.api.others.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumonusMemberListUpdateRequest {
	
	@NotNull
	private Long alumonusMemberId;
	
	@NotNull
	private Long cmsId;
	
	private Integer serial;

	
	private String name;
	
	
	private String designation;
	
	
	private String organization;
	
	
	private String batch;
	
	
	private String details;
	
	
	private String contactNo;
	
	
	private String email;
	
	@NotEmpty
	private String modifiedBy;
	
	private String imageName;
	
	private byte[] fileContent;
	
	private boolean fileSaveOrEditable;
	
	
}
