package com.netizen.cms.api.cmsinfo.model.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlInfos {
	
    private Long cmsId;	
	
	private String urlName;

}
