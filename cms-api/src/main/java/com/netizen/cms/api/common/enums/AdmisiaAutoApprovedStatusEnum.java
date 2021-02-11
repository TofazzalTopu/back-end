package com.netizen.cms.api.common.enums;

public enum AdmisiaAutoApprovedStatusEnum {

	
	AUTO_APPROVE_STATUS_YES(1, "Yes"),

	AUTO_APPROVE_STATUS_NO(0, "No");

	private AdmisiaAutoApprovedStatusEnum() {
	}

	private int code;
	private String description;

	private AdmisiaAutoApprovedStatusEnum(int code, String description) {
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
		for (AdmisiaAutoApprovedStatusEnum e : values()) {
			if (e.code == code) {
				return e.getDescription();
			}
		}
		return "";
	}

	public static boolean containsCode(int code) {
		for (AdmisiaAutoApprovedStatusEnum e : values()) {
			if (e.code == code) {
				return true;
			}
		}
		return false;
	}
}
