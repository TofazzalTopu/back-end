package com.netizen.cms.api.gallery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoGalleryViewResponse {
	
	private Long photoGalleryId;

	private String photoType;

	private String photoTitle;

	private String photoDetails;
	
	private int photoSerial;
	
	private String fileName;
	
	private byte[] fileContent;
}
