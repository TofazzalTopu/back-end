package com.netizen.cms.api.official.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoViewExpiredResponse {
	
	private Long noticeId;
	
	private String noticeTitle;	
	
	private String noticeDetails;

	private String noticeFileName;

	private int noticeSerial;

	private int noticeStatus;

	private Date noticeIssueDate;

	private Date noticeExpiryDate;
	
	private String expireStatus;

}
