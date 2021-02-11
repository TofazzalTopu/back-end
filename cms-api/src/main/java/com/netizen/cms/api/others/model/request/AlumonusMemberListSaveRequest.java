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
public class AlumonusMemberListSaveRequest {

	@NotNull
	private Long cmsId;
	
	private Integer serial;

	@NotEmpty
	private String name;
	
	@NotEmpty
	private String designation;
	
	@NotEmpty
	private String organization;
	
	@NotEmpty
	private String batch;
	
	@NotEmpty
	private String details;
	
	@NotEmpty
	private String contactNo;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String createBy;
	
	private String imageName;
	
	private byte[] fileContent;
	
	private boolean fileSaveOrEditable;
	
	
}
