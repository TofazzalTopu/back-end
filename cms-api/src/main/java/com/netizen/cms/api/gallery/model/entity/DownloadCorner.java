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
@Table(name="download_corner")
public class DownloadCorner implements Serializable{
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="file_id")
	private Long fileId;	
	
	@Column(name = "file_create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fileCreateDate;
	
	@Column(name="file_title", nullable = false)
	private String fileTitle;
	
	@Column(name="file_serial", columnDefinition = "Integer default 0")
	private int fileSerial;
	
	@Column(name="file_name")
	private String fileName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
}
