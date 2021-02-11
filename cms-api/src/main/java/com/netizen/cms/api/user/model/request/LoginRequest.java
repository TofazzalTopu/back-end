package com.netizen.cms.api.user.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank
	private Long roleAssignId;

}
