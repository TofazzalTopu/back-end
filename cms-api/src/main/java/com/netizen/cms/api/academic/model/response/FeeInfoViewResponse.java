package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeInfoViewResponse {

	private Long feeId;		
	private String feeName;	
	private String feeType;	
	private Double feeAmount;	
	private Integer feeSerial;		
	private String feePaymentMode;	
	private Long classId;	
	private String className;
	private Long groupId;	
	private String groupName;
}
