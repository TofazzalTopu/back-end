package com.netizen.cms.api.admisia.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "admisia_core_config")
public class AdmisiaCoreConfig implements Serializable {

	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "core_config_id")
	private Long coreConfigId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", unique = true, nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "current_admission_year", nullable = false)
	private Integer currentAdmissionYear;
	
	@Column(name = "service_charge", nullable = false)
    private double serviceCharge;

	@Column(name = "config_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date configDate;	

	@Column(name = "circular_title", nullable = false)
	private String circularTitle;

	@Column(name = "circular_image", nullable = false)
	private String circularImage;
	
	

}
