package com.netizen.cms.api.admisia.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdmisiaClassConfigViewResponse {
	
	private Long classConfigId;

    private int classConfigSerial;	 
    
    private String className;
    
    private String groupName;

    private Date applicationStartDate;
   
    private Date applicationEndDate;

    private int applicantLimit;

    private double applicationFee;
    
    private double serviceCharge;
    
    private double totalFee;

    private int enableStatus;

    private int admissionExamStatus;
    
    private Date admissionExamDate;

    private String admissionExamInstruction;

    private int autoApproveStatus;

    private int prevExamInfoRequiredStatus;
 
    private Date admissionExamTime;
    
   // private String admissionExamTime;
    
	private String examCenterName;
    
	private String signatureTitle;

	private String fileName;

	private byte[] fileContent;

}
