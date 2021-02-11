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
@Table(name = "member_info")
public class MemberInfo implements Serializable {

	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "member_name", nullable = false)
	private String memberName;

	@Column(name = "member_designation", nullable = false)
	private String memberDesignation;

	@Column(name = "member_email")
	private String memberEmail;

	@Column(name = "member_mobile")
	private String memberMobile;

	@Column(name = "member_type", nullable = false)
	private String memberType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "member_status", columnDefinition = "int default 0")
	private int memberStatus;

	@Column(name = "member_img_name")
	private String memberImgName;

	@Column(name = "member_serial", columnDefinition = "Integer default 0")
	private int memberSerial;

	@Column(name = "facebook_profile")
	private String facebookProfile;

	@Column(name = "linkedin_profile")
	private String linkedinProfile;

	@Column(name = "twitter_profile")
	private String twitterProfile;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

}
