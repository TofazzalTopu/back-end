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
public class GroupInfoSaveRequest {
	
	@NotBlank
	private String groupName;

	private int groupSerial;
	
	@NotNull
	private Long cmsId;

}
