/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netizen.cms.api.user.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

/**
 *
 * @author riad
 */
@Entity
@Table(name = "users")
public class Users implements Serializable/* , UserDetails */{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6877843634326331846L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@ManyToOne
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

	@Column(name = "nick_name")
	private String nickName;

	@Transient
	private List<String> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * @Override public String getUsername() { return username; }
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * @Override public String getPassword() { return password; }
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 * 
	 * @Override public boolean isEnabled() { return enabled; }
	 * 
	 * public void setEnabled(boolean enabled) { this.enabled = enabled; }
	 * 
	 * 
	 * public CmsInfo getCmsInfo() { return cmsInfo; }
	 * 
	 * public void setCmsInfo(CmsInfo cmsInfo) { this.cmsInfo = cmsInfo; }
	 * 
	 * public String getNickName() { return nickName; }
	 * 
	 * public void setNickName(String nickName) { this.nickName = nickName; }
	 * 
	 * public List<String> getRoles() { return roles; }
	 * 
	 * public void setRoles(List<String> roles) { this.roles = roles; }
	 * 
	 * /////////////////////////////////////////////////
	 * 
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
	 * this.roles.stream().forEach((s) -> { authorities.add(new
	 * SimpleGrantedAuthority(s)); }); return authorities; }
	 * 
	 * @Override public boolean isAccountNonExpired() { return true; }
	 * 
	 * @Override public boolean isAccountNonLocked() { return true; }
	 * 
	 * @Override public boolean isCredentialsNonExpired() { return true; }
	 */

}
