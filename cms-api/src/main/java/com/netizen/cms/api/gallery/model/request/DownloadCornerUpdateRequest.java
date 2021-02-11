package com.netizen.cms.api.gallery.model.request;

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
public class DownloadCornerUpdateRequest {

	@NotNull
	private Long fileId;

	@NotEmpty
	private String fileTitle;

	private int fileSerial;

	private String fileName;
	
	@NotNull
	private Long cmsId;

	private byte[] fileContent;
	private boolean fileSaveOrEditable;

}
