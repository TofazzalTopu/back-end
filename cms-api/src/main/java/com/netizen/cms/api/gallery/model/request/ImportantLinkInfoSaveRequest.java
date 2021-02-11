package com.netizen.cms.api.gallery.model.request;

import javax.validation.constraints.NotBlank;
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
public class ImportantLinkInfoSaveRequest {
	
	@NotEmpty
	private String linkTitle;
	
	@NotEmpty
	private String linkUrl;

	private int linkSerial;
	
	@NotNull
	private Long cmsId;
	
	@NotBlank
	private String username;

}
