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
@Table(name="class_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id", "class_name"}))
public class ClassInfo implements Serializable{

	private static final long serialVersionUID = 3318897804425788561L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="class_id")
	private Long classId;
	
	@Column(name="class_name", nullable = false)
	private String className;
	
	@Column(name="class_serial", columnDefinition = "Integer default 0")
	private Integer classSerial;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
}
