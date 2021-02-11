package com.netizen.cms.api.admisia.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MarkInputRequest {

	
	@NotNull	
	private Long cmsId;

	@Size(min = 1)
	private List<MarkInputRequestHelper> details;

	public Long getCmsId() {
		return cmsId;
	}
	
	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}
	
	public List<MarkInputRequestHelper> getDetails() {
		return details;
	}
	
	public void setDetails(List<MarkInputRequestHelper> details) {
		this.details = details;
	}
	  
  
  
  
  
	
	
}
