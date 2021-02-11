package com.netizen.cms.api.gallery.model.entity;

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
@Table(name="important_link_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id", "link_url"}))
public class ImportantLinkInfo  implements Serializable{
	
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="link_id")
	private Long linkId;
	
	@Column(name="link_title", nullable = false)
	private String linkTitle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name="link_url", nullable = false)
	private String linkUrl;
	
	@Column(name="link_serial", columnDefinition = "Integer default 0")
	private int linkSerial;
	
	@Column(name = "link_create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date linkCreateDate;	
	
	@Column(name="link_status", columnDefinition = "int default 0")
	private int linkStatus;
	
	@Column(name="created_by", nullable = false)
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;	
	
	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

}
