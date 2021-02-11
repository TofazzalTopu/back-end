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
@Table(name = "event_log")
public class EventLog implements Serializable {
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "event_title", nullable = false)
	private String eventTitle;

	@Column(name = "event_serial", columnDefinition = "Integer default 0")
	private int eventSerial;

	@Column(name = "event_details", nullable = false)
	private String eventDetails;

	@Column(name = "event_type", nullable = false)
	private String eventType;

	@Column(name = "event_start_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventStartDate;

	@Column(name = "event_end_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventEndDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "event_status", columnDefinition = "int default 0")
	private int eventStatus;

}
