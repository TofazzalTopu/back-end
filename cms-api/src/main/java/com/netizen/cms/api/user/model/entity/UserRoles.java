/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netizen.cms.api.user.model.entity;

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

/**
 *
 * @author riad
 */
@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "rolename"}))
public class UserRoles implements Serializable {
	private static final long serialVersionUID = 3318897804425788561L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "rolename", nullable = false)
    private String roleName;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cms_id", nullable = false)
	private CmsInfo cmsInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public CmsInfo getCmsInfo() {
		return cmsInfo;
	}

	public void setCmsInfo(CmsInfo cmsInfo) {
		this.cmsInfo = cmsInfo;
	}
    
    
    
}
