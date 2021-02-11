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
@Table(name = "photo_gallery")
public class PhotoGallery implements Serializable {
	private static final long serialVersionUID = -6350428861288025537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_gallery_id")
	private Long photoGalleryId;

	@Column(name = "photo_type", nullable = false)
	private String photoType;

	@Column(name = "photo_title", nullable = false)
	private String photoTitle;

	@Column(name = "photo_details")
	private String photoDetails;

	@Column(name = "photo_serial", columnDefinition = "Integer default 0")
	private int photoSerial;

	@Column(name = "photo_status", columnDefinition = "int default 0")
	private int photoStatus;

	@Column(name = "photo_create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date photoCreateDate;

	@Column(name = "photo_name")
	private String photoName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;
}
