package com.netizen.cms.api.cmsinfo.model.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsEdumanMappingUpdateRequest {
	
	@NotNull
	private Long cmsMappingId;
	
	private int approvedStatus;

}
