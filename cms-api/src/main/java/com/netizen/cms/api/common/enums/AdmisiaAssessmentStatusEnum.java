package com.netizen.cms.api.common.enums;

public enum AdmisiaAssessmentStatusEnum {

	
	ASSESSMENT_STATUS_YES(1, "Yes"),
	ASSESSMENT_STATUS_NO(0, "No");

	private AdmisiaAssessmentStatusEnum() {
	}

	private int code;
	private String description;

	private AdmisiaAssessmentStatusEnum(int code, String description) {
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
		for (AdmisiaAssessmentStatusEnum e : values()) {
			if (e.code == code) {
				return e.getDescription();
			}
		}
		return "";
	}

	public static boolean containsCode(int code) {
		for (AdmisiaAssessmentStatusEnum e : values()) {
			if (e.code == code) {
				return true;
			}
		}
		return false;
	}
}
