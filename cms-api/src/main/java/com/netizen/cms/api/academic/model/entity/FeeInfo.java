package com.netizen.cms.api.academic.model.entity;

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
import javax.persistence.UniqueConstraint;

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
@Table(name = "fee_info", uniqueConstraints = @UniqueConstraint(columnNames = {"class_id", "group_id","cms_id","fee_name"}))
public class FeeInfo implements Serializable {

	private static final long serialVersionUID = -6197089213595702501L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fee_id")
	private Long feeId;

	@Column(name = "fee_name", nullable = false)
	private String feeName;
	
	@Column(name = "fee_type", nullable = false)
	private String feeType;

	@Column(name = "fee_amount", columnDefinition = "Double default 0")
	private Double feeAmount;

	@Column(name = "fee_serial", columnDefinition = "Integer default 0")
	private Integer feeSerial;

	@Column(name = "fee_payment_mode", nullable = false)
	private String feePaymentMode;

	@Column(name = "fee_create_date", nullable = false)
	private Date feeCreateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	private ClassInfo classInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private GroupInfo groupInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

}
