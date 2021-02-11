/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netizen.cms.api.menuconfig.model.entity;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "cms_menu_config", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id","menu_item_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_config_id")
    private Long menuConfigId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cms_id", nullable = false)
    private CmsInfo cmsInfo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private CmsMenuItem cmsMenuItem;
    
    @Column(name = "assign_user_id", nullable = false)
	private String assign_user_id;
    
	@Column(name = "assign_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date assignDate;
}
