package com.netizen.cms.api.others.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumonusMemberListViewResponse {

	private Long alumonusMemberId;
	
	private Integer serial;
	
	private String name;
	
	private String designation;
	
	private String organization;
	
	private String batch;
	
	private String details;	
	
	private String contactNo;
	
	private String email;
	
	private String imageName;
	
	private Date createDate;
	
	private String createBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	
	private byte[] imgContent;

}
