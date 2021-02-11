package com.netizen.cms.api.admisia.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDetailsViewResponse {
	
	ApplicantPersonalViewResponse applicantPersonalViewResponse;
	
	List<ApplicantPreviousExamViewResponse> applicantPreviousExamViewResponses;

}
