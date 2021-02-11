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
@Table(name = "speech_info")
public class SpeechInfo implements Serializable {

	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "speech_id")
	private Long speechId;

	@Column(name = "speech_details", nullable = false)
	private String speechDetails;

	@Column(name = "speaker_img_name")
	private String speakerImgName;

	@Column(name = "speech_serial", columnDefinition = "int default 0")
	private int speechSerial;

	@Column(name = "speech_status", columnDefinition = "int default 0")
	private int speechStatus;

	@Column(name = "speech_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date speechDate;

	@Column(name = "speaker_name", nullable = false)
	private String speakerName;

	@Column(name = "speaker_designation", nullable = false)
	private String speakerDesignation;

	@Column(name = "speaker_email")
	private String speakerEmail;

	@Column(name = "speaker_mobile")
	private String speakerMobile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "speaker_facebook_linke")
	private String speakerFacebookLinke;

	@Column(name = "speaker_twitter_linke")
	private String speakerTwitterLinke;

	@Column(name = "speaker_linkedin_linke")
	private String speakerLinkedinLinke;
	
	@Column(name = "welcome_speech_status", columnDefinition = "int default 0")
	private Integer welcomeSpeechStatus;

}
