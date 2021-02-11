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
public class DownloadCornerSaveRequest {

	@NotEmpty
	private String fileTitle;

	private int fileSerial;

	@NotNull
	private Long cmsId;

	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;

}
