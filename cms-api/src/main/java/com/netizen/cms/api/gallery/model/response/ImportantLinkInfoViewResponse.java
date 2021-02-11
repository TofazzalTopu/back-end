package com.netizen.cms.api.gallery.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportantLinkInfoViewResponse {
	
	private Long linkId;

	private String linkTitle;

	private String linkUrl;

	private int linkSerial;

}
