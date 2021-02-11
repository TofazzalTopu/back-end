package com.netizen.cms.api.academic.model.entity;

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
@Table(name = "group_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id", "group_name"}))
public class GroupInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "group_name", nullable = false)
	private String groupName;

	@Column(name = "group_serial", columnDefinition = "Integer default 0")
	private Integer groupSerial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

}
