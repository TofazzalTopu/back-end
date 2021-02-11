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
@Table(name = "admisia_fee_transaction_log")
public class AdmisiaFeeTransactionLog implements Serializable {

	private static final long serialVersionUID = -7726893041042469950L;

	@Id
	@Column(name = "fee_transaction_log_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feeTransactionLogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false)
	private AdmisiaApplicantInfo admisiaApplicantInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;
	
	@Column(name = "registration_id", nullable = false, unique = true)
	private String registrationId;

	@Column(name = "bank_transaction_id", nullable = false, unique = true)
	private String bankTransactionId;

	@Column(name = "transaction_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date transactionDate;
	
	@Column(name = "validated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date validatedOn;
	
	@Column(name = "transaction_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionTime; 

	@Column(name = "log_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDate;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "store_amount")
	private double storeAmount;
	
	@Column(name = "val_id")
	private String valId;
	
	@Column(name = "card_ref_id")
	private String cardRefId;
	
	@Column(name = "application_fee")
	private double applicationFee;
	
	@Column(name = "service_charge")
	private double serviceCharge;

}
