package com.netizen.cms.api.academic.model.request;

import javax.validation.constraints.NotBlank;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoUpdateRequest {
	
	@NotNull
	private Long groupId;

	@NotBlank
	private String groupName;

	private Integer groupSerial;
	
	@NotNull
	private Long cmsId;

}
