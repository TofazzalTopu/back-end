package com.netizen.cms.api.cmsinfo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsEdumanMappingViewResponse {
	
	private Long cmsMappingId;
	
	private String instituteId;
	
	private String instituteName;
	
	private String instituteContact;
	
	private int approvedStatus;
	
	private Long cmsId;
	
	private String urlName;
}
