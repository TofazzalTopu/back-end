package com.netizen.cms.api.admisia.model.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceChargeUpdateRequest {
	
	@NotNull
	private Long coreConfigId;

	@NotNull
	private Long cmsId;
	
	private double serviceCharge;

}
