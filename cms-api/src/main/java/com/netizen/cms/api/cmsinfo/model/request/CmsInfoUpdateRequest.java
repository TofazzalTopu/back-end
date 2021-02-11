package com.netizen.cms.api.cmsinfo.model.request;

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
public class CmsInfoUpdateRequest {
	
	@NotNull
	private Long cmsId;	
	
	@NotEmpty
	private String instituteName;

	@NotEmpty
	private String instituteAddress;

	@NotEmpty
	private String instituteContact;

	@NotEmpty
	private String instituteEmail;
	
	private String logoName;
	
	private byte[] logoContent;
	
	private boolean logoSaveOrEditable;
	
	private String storePasswd;;

	

}
