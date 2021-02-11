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
public class FeeInfoSaveRequest {

	@NotBlank
	private String feeName;
	
	@NotBlank
	private String feeType;	
	
	@NotNull
	private Double feeAmount;
	
	private Integer feeSerial;
	
	@NotBlank
	private String feePaymentMode;
	
	@NotNull
	private Long classId;

	@NotNull
	private Long groupId;
	
	@NotNull
	private Long cmsId;
}
