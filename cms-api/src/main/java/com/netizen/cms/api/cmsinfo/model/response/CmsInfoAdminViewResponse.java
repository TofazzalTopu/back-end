package com.netizen.cms.api.cmsinfo.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsInfoAdminViewResponse {
	
	private Long cmsId;
	
	private Long customCmsId;

	private String urlName;

	private String logoName;

	private String instituteName;

	private String instituteAddress;

	private String instituteContact;

	private String instituteEmail;

	private int mappedStatus;
	
	private Date cmsCreateDate;

	private byte[] logoContent;
	
	private String storeId;
	
	private String storePasswd;

}
