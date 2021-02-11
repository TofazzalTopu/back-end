package com.netizen.cms.api.official.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice_info")
public class NoticeInfo implements Serializable {
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long noticeId;

	@Column(name = "notice_title", nullable = false)
	private String noticeTitle;

	@Column(name = "notice_details", nullable = false)
	private String noticeDetails;

	@Column(name = "notice_file_name")
	private String noticeFileName;

	@Column(name = "notice_serial", columnDefinition = "Integer default 0")
	private int noticeSerial;

	@Column(name = "notice_status", columnDefinition = "int default 0")
	private int noticeStatus;

	@Column(name = "notice_create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date noticeCreateDate;

	@Column(name = "notice_issue_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date noticeIssueDate;

	@Column(name = "notice_expiry_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date noticeExpiryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
}
