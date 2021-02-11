package com.netizen.cms.api.admisia.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaCoreConfigViewResponse {
	
	private Long coreConfigId;

	private Integer currentAdmissionYear;
	
	private double serviceCharge;

	private Date configDate;

	private String circularTitle;
	
	private String fileName;
	
	private byte[] fileContent;

}
