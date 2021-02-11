package com.netizen.cms.api.cmsinfo.model.entity;

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
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cms_eduman_mapping", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id","institute_id"}))
public class CmsEdumanMapping implements Serializable{

	private static final long serialVersionUID = -8745932967992993050L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mapping_id")
	private Long cmsMappingId;
	
	@Column(name="institute_id", nullable = false)
	private String instituteId;
	
	@Column(name="institute_name", nullable = false)
	private String instituteName;
	
	@Column(name="institute_contact", nullable = false)
	private String instituteContact;
	
	@Column(name="approved_status", columnDefinition = "Integer default 0")
	private int approvedStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name="purchase_code", nullable = false)
	private String purchaseCode;
	
	@Column(name = "mapped_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mappedDate;
	
	
	@Column(name = "approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;
	
	
	
}
