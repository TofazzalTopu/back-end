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
public class CmsEdumanMappingSaveRequest {

	@NotEmpty
	private String instituteId;
	
	@NotEmpty
	private String instituteName;
	
	@NotEmpty
	private String instituteContact;;
	
	@NotNull
	private Long cmsId;
	
	@NotEmpty
	private String purchaseCode;

}
