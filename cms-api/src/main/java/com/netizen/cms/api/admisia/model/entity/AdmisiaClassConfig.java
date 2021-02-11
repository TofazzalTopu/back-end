package com.netizen.cms.api.admisia.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admisia_class_config", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id","class_id","group_id"}))
public class AdmisiaClassConfig implements Serializable {

	private static final long serialVersionUID = -6350428861288025537L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_config_id")
	private Long classConfigId;

    @Column(name = "class_config_serial", nullable = false)
    private int classConfigSerial;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "core_config_id", nullable = false)
    private AdmisiaCoreConfig admisiaCoreConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassInfo classInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupInfo groupInfo;

    @Column(name = "application_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date applicationStartDate;
   
    @Column(name = "application_end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date applicationEndDate;

    @Column(name = "applicant_limit", nullable = false)
    private int applicantLimit;

    @Column(name = "application_fee", nullable = false)
    private double applicationFee;
    
    @Column(name = "service_charge", nullable = false)
    private double serviceCharge;
    
    @Column(name = "total_fee", nullable = false)
    private double totalFee;

    @Column(name = "enable_status", columnDefinition = "Integer default 0")
    private int enableStatus;

    @Column(name = "admission_exam_status", columnDefinition = "Integer default 0")
    private int admissionExamStatus;
    
    @Column(name = "admission_exam_date")
    @Temporal(TemporalType.DATE)
    private Date admissionExamDate;

    @Column(name = "admission_exam_instruction")
    private String admissionExamInstruction;

    @Column(name = "auto_approve_status", columnDefinition = "Integer default 0")
    private int autoApproveStatus;

    @Column(name = "prev_exam_info_required_status", columnDefinition = "Integer default 0")
    private int prevExamInfoRequiredStatus;
    
    @Column(name = "admission_exam_time")
    @Temporal(TemporalType.TIME)
    private Date admissionExamTime;
    
    @Column(name = "exam_center_name")
	private String examCenterName;
    
    @Column(name = "signature_title", nullable = false)
	private String signatureTitle;

	@Column(name = "signature_image")
	private String signatureImage;
   
}
