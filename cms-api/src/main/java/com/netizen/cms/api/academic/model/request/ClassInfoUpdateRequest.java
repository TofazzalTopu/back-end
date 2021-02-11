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
public class ClassInfoUpdateRequest {

	@NotNull
	private Long classId;
	
	@NotNull
	@NotBlank
	private String className;
	
	@NotNull
	private Integer classSerial;
	
	@NotNull
	private Long cmsId;
	

}
