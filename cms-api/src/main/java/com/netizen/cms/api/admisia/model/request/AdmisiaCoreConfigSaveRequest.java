package com.netizen.cms.api.admisia.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaCoreConfigSaveRequest {

	@NotNull
	private Long cmsId;
	
	private Integer currentAdmissionYear;	
	
	@NotEmpty
	private String circularTitle;
	@NotEmpty
	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;
}
