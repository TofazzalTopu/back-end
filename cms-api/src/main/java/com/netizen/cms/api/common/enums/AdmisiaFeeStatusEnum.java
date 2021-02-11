package com.netizen.cms.api.common.enums;

public enum AdmisiaFeeStatusEnum {

	FEE_STATUS_UNPAID(0, "Unpaid"),
	FEE_STATUS_PAID(1, "Paid");

	private AdmisiaFeeStatusEnum() {
	}

	private int code;
	private String description;

	private AdmisiaFeeStatusEnum(int code, String description) {
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
		for (AdmisiaFeeStatusEnum e : values()) {
			if (e.code == code) {
				return e.getDescription();
			}
		}
		return "";
	}

	public static boolean containsCode(int code) {
		for (AdmisiaFeeStatusEnum e : values()) {
			if (e.code == code) {
				return true;
			}
		}
		return false;
	}
}
