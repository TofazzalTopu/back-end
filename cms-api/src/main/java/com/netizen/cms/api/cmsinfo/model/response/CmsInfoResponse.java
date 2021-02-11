package com.netizen.cms.api.cmsinfo.model.response;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsInfoResponse {

	
    private Long cmsId;	
    
    private Long customCmsId;
	
	private String urlName;
	
	private String applicationType;
	
	private String applicationPackage;
	
	private String applicationTheme;	
	
	private String logoName;
	
	private String defaultLanguage;
	
	private String instituteName;
	
	private String instituteAddress;
	
	private String instituteContact;
	
	private String instituteEmail;
	
	private int mappedStatus;
	
	private byte[] logoContent;
	
	private List<CmsInfoResponseHelper> edumanInstituteList;
	
	

	
	
}
