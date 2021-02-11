package com.netizen.cms.api.official.model.request;

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
public class MemberInfoUpdateRequest {


	@NotNull
	private Long memberId;

	@NotBlank
	private String memberName;

	@NotBlank
	private String memberDesignation;

	private String memberEmail;

	//@NotBlank
	private String memberMobile;

	@NotBlank
	private String memberType;

	private int memberStatus;

	private int memberSerial;

	private String facebookProfile;

	private String linkedinProfile;

	private String twitterProfile;
	
	@NotNull
	private Long cmsId;
	
	@NotBlank
	private String userName;
	
	private String imageName;
	private byte[] imageContent;
	private boolean imageSaveOrEditable;



}
