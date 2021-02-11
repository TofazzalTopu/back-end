package com.netizen.cms.api.gallery.model.request;

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
public class PhotoGalleryUpdateRequest {
	
	@NotNull
	private Long photoGalleryId;
	
	@NotBlank
	private String photoTitle;
	
	
	private String photoDetails;
	
	private int photoSerial;
	
	@NotNull
	private Long cmsId;
	
	private String fileName;
	private byte[] fileContent;
	private boolean fileSaveOrEditable;

}
