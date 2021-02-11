package com.netizen.cms.api.official.model.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoUpdateRequest {

	@NotNull
	private Long noticeId;

	@NotBlank
	private String noticeTitle;

	@NotBlank
	private String noticeDetails;

	private int noticeSerial;

	private int noticeStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date noticeIssueDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date noticeExpiryDate;

	@NotNull
	private Long cmsId;

	@NotBlank
	private String userName;

	private String fileName;

	private byte[] fileContent;

	private boolean fileSaveOrEditable;
}
