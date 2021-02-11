package com.netizen.cms.api.admisia.model.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaApplicantStatusUpdateRequest {
	
	@NotEmpty
	List<Long> applicantIds;
	
	@NotNull
    Long cmsId;
	
	@NotNull
    int applicantStatus;
	
}
