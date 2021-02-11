package com.netizen.cms.api.admisia.model.response;

public class AdmisiaClassConfigPublicView {

	private Long classConfigId;
	private String className;
	private String groupName;
	private Integer currentAcademicYear;
	private Long leftDays;
	private String applicationStartDate;
	private String applicationEndDate;
	private double applicationFee;
	private double serviceCharge;
	private Double totalFee;
	private Integer prevExamInfoRequiredStatus;
	private Integer applicantLimit;

	public Long getClassConfigId() {
		return classConfigId;
	}

	public void setClassConfigId(Long classConfigId) {
		this.classConfigId = classConfigId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCurrentAcademicYear() {
		return currentAcademicYear;
	}

	public void setCurrentAcademicYear(Integer currentAcademicYear) {
		this.currentAcademicYear = currentAcademicYear;
	}

	public Long getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(Long leftDays) {
		this.leftDays = leftDays;
	}

	public String getApplicationStartDate() {
		return applicationStartDate;
	}

	public void setApplicationStartDate(String applicationStartDate) {
		this.applicationStartDate = applicationStartDate;
	}

	public String getApplicationEndDate() {
		return applicationEndDate;
	}

	public void setApplicationEndDate(String applicationEndDate) {
		this.applicationEndDate = applicationEndDate;
	}
	
	
	public double getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(double applicationFee) {
		this.applicationFee = applicationFee;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getPrevExamInfoRequiredStatus() {
		return prevExamInfoRequiredStatus;
	}

	public void setPrevExamInfoRequiredStatus(Integer prevExamInfoRequiredStatus) {
		this.prevExamInfoRequiredStatus = prevExamInfoRequiredStatus;
	}

	public Integer getApplicantLimit() {
		return applicantLimit;
	}

	public void setApplicantLimit(Integer applicantLimit) {
		this.applicantLimit = applicantLimit;
	}

}
