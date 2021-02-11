package com.netizen.cms.api.cmsinfo.model.request;

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
public class CmsInfoCreateRequest {

	@NotNull
	private Long userRoleAssignId;
		
	@NotBlank
	private String urlName;

	private String applicationTheme;

	private String logoName;
	
	private byte[] logoContent;
	
	private boolean logoSaveOrEditable;

	private String defaultLanguage;
	
	private int mappedStatus;

	@NotBlank
	private String instituteName;

	@NotBlank
	private String instituteAddress;

	@NotBlank
	private String instituteContact;

	@NotBlank
	private String instituteEmail;

}
