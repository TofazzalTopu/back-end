package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoViewResponse {
	
	private Long groupId;
	
	private String groupName;
	
	private Integer groupSerial;

}
