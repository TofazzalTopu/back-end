package com.netizen.cms.api.academic.model.request;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusInfoUpdateRequest {

	@NotNull
	private Long syllabusId;
	
	private String syllabusFileName;
		
	private Integer syllabusSerial;
		
	@NotNull
	private Long classId;

	@NotNull
	private Long groupId;
	
	@NotNull
	private Long cmsId;
	
	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;
}
