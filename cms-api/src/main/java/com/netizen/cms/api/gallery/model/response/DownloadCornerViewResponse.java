package com.netizen.cms.api.gallery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadCornerViewResponse {
	
	private Long fileId;	
	
	private String fileTitle;
	
	private int fileSerial;
	
	private String fileName;

}
