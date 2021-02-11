package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusInfoViewResponse {

	private Long syllabusId;
	
	private String syllabusFileName;
	
	private byte[] fileContent;
		
	private Integer syllabusSerial;
	
	private Long classId;
	
	private String className;

	private Long groupId;
	
	private String groupName;
}
