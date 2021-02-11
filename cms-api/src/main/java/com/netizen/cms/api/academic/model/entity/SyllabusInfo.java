package com.netizen.cms.api.academic.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "syllabus_info", uniqueConstraints = @UniqueConstraint(columnNames = { "cms_id", "class_id", "group_id" }))

public class SyllabusInfo implements Serializable {

	private static final long serialVersionUID = -1938729330441830658L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "syllabus_id")
	private Long syllabusId;

	@Column(name = "syllabus_file_name")
	private String syllabusFileName;

	@Column(name = "syllabus_serial", columnDefinition = "Integer default 0")
	private Integer syllabusSerial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	private ClassInfo classInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private GroupInfo groupInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;
}
