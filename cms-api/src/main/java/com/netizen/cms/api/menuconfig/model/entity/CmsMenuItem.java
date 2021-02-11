package com.netizen.cms.api.menuconfig.model.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cms_menu_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_item_id")
	private Long menuItemId;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "default_code", nullable = false, unique = true)
	private String defaultCode;

	@Column(name = "enable_status", columnDefinition = "Integer default 0")
	private int enableStatus;

	@Column(name = "parent_status", columnDefinition = "Integer default 0")
	private int parentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_menu_item_id")
	private CmsMenuItem parentCmsMenuItem;

	@Column(name = "menu_layer", nullable = false)
	private int menuLayer;

	@Column(name = "note")
	private String note;

}
