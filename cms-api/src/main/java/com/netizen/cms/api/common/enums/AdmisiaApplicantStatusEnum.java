package com.netizen.cms.api.common.enums;

public enum AdmisiaApplicantStatusEnum {
	
	PENDING_FOR_ASSESSMENT(0, "Pending for Assessment"), 
	REJECTED_FOR_ASSESSMENT(2, "Rejected for Assessment"),
	PENDING_FOR_ADMISSION(1, "Pending for Admission"), 
	APPROVED_FOR_ADMISSION(5, "Approved for Admission"),
	REJECTED_FOR_ADMISSION(3, "Rejected for Admission"), 
	WAITING_FOR_ADMISSION(4, "Waiting for Admission"),
	TRANSFERRED_FOR_ADMISSION(10, "Transferred for Admission");

	private AdmisiaApplicantStatusEnum() {
	}

	private int code;
	private String description;

	private AdmisiaApplicantStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static String getByCode(int code) {
		for (AdmisiaApplicantStatusEnum e : values()) {
			if (e.code == code) {
				return e.getDescription();
			}
		}
		return "";
	}

	public static boolean containsCode(int code) {
		for (AdmisiaApplicantStatusEnum e : values()) {
			if (e.code == code) {
				return true;
			}
		}
		return false;
	}
}
