package com.netizen.cms.api.admisia.model.request;

public class MarkInputRequestHelper {
	
	private Long applicantId;
	private Double examMarks;
	private String examResult;
	
	public Long getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}
	public Double getExamMarks() {
		return examMarks;
	}
	public void setExamMarks(Double examMarks) {
		this.examMarks = examMarks;
	}
	public String getExamResult() {
		return examResult;
	}
	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}

}
