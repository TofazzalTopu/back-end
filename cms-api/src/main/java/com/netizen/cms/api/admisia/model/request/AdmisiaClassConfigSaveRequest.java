package com.netizen.cms.api.admisia.model.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisiaClassConfigSaveRequest {

	private int classConfigSerial;
	
	@NotNull
	private Long cmsId;

	private Long coreConfigId;

	@NotNull
	private Long classId;

	@NotNull
	private Long groupId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date applicationStartDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date applicationEndDate;
	
	@NotNull
	private int applicantLimit;
	
	@NotNull
	private double applicationFee;
	
	private int admissionExamStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date admissionExamDate;		

	private String admissionExamTime;
	
	private String admissionExamInstruction;
	
	private String examCenterName;
	
	private int autoApproveStatus;;
	
	private int prevExamInfoRequiredStatus;
	
	private String signatureTitle;

	private String fileName;
	
	private byte[] fileContent;
	
	private boolean fileSaveOrEditable;

}
