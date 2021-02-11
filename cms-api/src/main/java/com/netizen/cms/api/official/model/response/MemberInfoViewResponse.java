package com.netizen.cms.api.official.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoViewResponse {

	private Long memberId;

	private String memberName;

	private String memberDesignation;

	private String memberEmail;

	private String memberMobile;

	private String memberType;

	private int memberStatus;

	private String memberImgName;

	private int memberSerial;

	private String facebookProfile;

	private String linkedinProfile;

	private String twitterProfile;
	
	private byte[] memberImg;


	
}
