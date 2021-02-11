package com.netizen.cms.api.admisia.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.netizen.cms.api.admisia.common.AdmisiaUtils;
import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantInfo;
import com.netizen.cms.api.admisia.model.entity.AdmisiaFeeTransactionLog;
import com.netizen.cms.api.admisia.repository.AdmisiaApplicantInfoRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaFeeTransactionLogRepository;
import com.netizen.cms.api.common.enums.AdmisiaApplicantStatusEnum;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@Service
public class AdmisiaApplicationFeeService {

	@Autowired
	public AdmisiaApplicantInfoRepository admisiaApplicantInfoRepository;

	@Autowired
	public AdmisiaFeeTransactionLogRepository admisiaFeeTransactionLogRepository;

	public ItemResponse redirectToSSLGatewayPaymentPage(String registrationId) {

		ItemResponse itemResponse = new ItemResponse();

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository.findByRegistrationId(registrationId);

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessage("No Info Found.");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		if (admisiaApplicantInfo.getApplicantFeeStatus() == 1) {
			itemResponse.setMessage("Already Paid");
			itemResponse.setMessageType(2);
			return itemResponse;
		}

		double totalamount = admisiaApplicantInfo.getAdmisiaClassConfig().getTotalFee();
		double applicationFee=admisiaApplicantInfo.getAdmisiaClassConfig().getApplicationFee();
		double serviceCharge=admisiaApplicantInfo.getAdmisiaClassConfig().getServiceCharge();
		
		String sslStoreId=admisiaApplicantInfo.getCmsInfo().getStoreId();
		String sslStorePassword=admisiaApplicantInfo.getCmsInfo().getStorePasswd();

		String requestBody = "store_id=" + sslStoreId + "&";
		requestBody += "store_passwd=" + sslStorePassword + "&";
		requestBody += "total_amount=" + totalamount + "&";
		requestBody += "tran_id=" + registrationId + "&";
		requestBody += "cus_name=" + admisiaApplicantInfo.getApplicantName() + "&";
		
		requestBody += "value_a=" + applicationFee + "&";
		requestBody += "value_b=" + serviceCharge + "&";
		requestBody += "value_c=" + admisiaApplicantInfo.getCmsInfo().getCustomCmsId() + "&";
		
		requestBody += "cus_email=" + "cust@yahoo.com" + "&";
		requestBody += "cus_add1=" + "Dhaka" + "&";
		requestBody += "cus_add2=" + "Dhaka" + "&";
		requestBody += "cus_city=" + "Dhaka" + "&";
		requestBody += "cus_country=" + "Bangladesh" + "&";
		requestBody += "cus_phone=" + admisiaApplicantInfo.getMobileNo() + "&";
		requestBody += "cus_fax=" + "01711111111" + "&";
		requestBody += "cus_fax=" + "01711111111" + "&";
		requestBody += "ship_name=" + "Riad" + "&";
		requestBody += "shipping_method=" + "No" + "&";
		requestBody += "product_name=" + "ApplicationFee" + "&";
		requestBody += "product_category=" + "Fees" + "&";
		requestBody += "product_profile=" + "ApplicationFees" + "&";
		requestBody += "success_url=" + AdmisiaUtils.SSL_SUCCESS_URL + "&";
		requestBody += "fail_url=" + AdmisiaUtils.SSL_FAILED_URL + "&";
		requestBody += "cancel_url=" + AdmisiaUtils.SSL_CANCEL_URL + "&";
		
		requestBody += "disbursements_acct=[{\"sslcz_ref_id\":\"servicecharge\",\"amount\":\""+serviceCharge+"\"},{\"sslcz_ref_id\":\"schoolpay\",\"amount\":\""+applicationFee+"\"}]";


		HttpResponse<String> response = null;
		try {
			response = Unirest.post(AdmisiaUtils.SSL_SESSION_ID_REQUEST)
					.header("content-type", "application/x-www-form-urlencoded").header("cache-control", "no-cache")
					.body(requestBody).asString();
		} catch (UnirestException e) {
			itemResponse.setMessage("OK");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		JSONObject jsonObject = new JSONObject(response.getBody());

		String gatewayPageURL = jsonObject.getString("GatewayPageURL");
		
		itemResponse.setItem(gatewayPageURL);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	@Transactional
	public ItemResponse validatePayment(HttpServletRequest request)
			throws UnsupportedEncodingException, ParseException {

		ItemResponse itemResponse = new ItemResponse();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		

		
		String registrationId = request.getParameter("tran_id");

		AdmisiaApplicantInfo admisiaApplicantInfo = admisiaApplicantInfoRepository.findByRegistrationId(registrationId);

		if (admisiaApplicantInfo == null) {
			itemResponse.setMessage("No Info Found.");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		if (admisiaApplicantInfo.getApplicantFeeStatus() == 1) {
			itemResponse.setMessage("Already Paid");
			itemResponse.setMessageType(2);
			return itemResponse;
		}
		
		
		String storeId=admisiaApplicantInfo.getCmsInfo().getStoreId();
		String storepassword=admisiaApplicantInfo.getCmsInfo().getStorePasswd();
		
		String encodedValID = URLEncoder.encode(request.getParameter("val_id"), Charset.forName("UTF-8").displayName());
		String encodedStoreID = URLEncoder.encode(storeId, Charset.forName("UTF-8").displayName());
		String encodedStorePassword = URLEncoder.encode(storepassword,Charset.forName("UTF-8").displayName());

		String url = AdmisiaUtils.SSL_VALIDATION_REQUEST + "?val_id=" + encodedValID + "&store_id=" + encodedStoreID
				+ "&store_passwd=" + encodedStorePassword + "&v=1&format=json";

		HttpResponse<String> response = null;
		try {
			response = Unirest.get(url).asString();
		} catch (UnirestException e) {
			itemResponse.setMessage("OK");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		JSONObject jsonObject = new JSONObject(response.getBody());

		String status = jsonObject.getString("status");

		String validatedOn = jsonObject.getString("validated_on");
		Date validatedDate = df.parse(validatedOn);
		String trnId = jsonObject.getString("tran_id");
		String valId = jsonObject.getString("val_id");
		String trnDate = jsonObject.getString("tran_date");
		Date transactionDate = df.parse(trnDate);
		Double amount = Double.parseDouble(jsonObject.getString("amount"));
		
		Double applicationFee = Double.parseDouble(jsonObject.getString("value_a"));
		Double serviceCharge = Double.parseDouble(jsonObject.getString("value_b"));
		
		String bankTrnId = jsonObject.getString("bank_tran_id");
		Double storeAmount = Double.parseDouble(jsonObject.getString("store_amount"));
		String cardReferenceId = jsonObject.getString("card_ref_id");
		String cardType = jsonObject.getString("card_type");

		if (status != null && status.equals("VALID")) {

			AdmisiaFeeTransactionLog transactionLog = new AdmisiaFeeTransactionLog();
			transactionLog.setAdmisiaApplicantInfo(admisiaApplicantInfo);
			transactionLog.setAmount(amount);
			transactionLog.setBankTransactionId(bankTrnId);
			transactionLog.setCardRefId(cardReferenceId);
			transactionLog.setCardType(cardType);
			transactionLog.setCmsInfo(admisiaApplicantInfo.getCmsInfo());
			transactionLog.setLogDate(new Date());
			transactionLog.setRegistrationId(trnId);
			transactionLog.setStoreAmount(storeAmount);
			transactionLog.setApplicationFee(applicationFee);
			transactionLog.setServiceCharge(serviceCharge);
			transactionLog.setTransactionDate(transactionDate);
			transactionLog.setTransactionTime(transactionDate);
			transactionLog.setValId(valId);
			transactionLog.setValidatedOn(validatedDate);

			admisiaFeeTransactionLogRepository.save(transactionLog);

			changeApplicantStatus(admisiaApplicantInfo);



		}

		itemResponse.setMessage("Your Transaction ID is " + bankTrnId);
		itemResponse.setMessageType(1);

		return itemResponse;
	}
  

  

  
  @Transactional	
  public BaseResponse admisiaApplicationFeeEnquiry(Long cmsId,String registrationId) throws ParseException {
		
	    BaseResponse baseResponse=new BaseResponse();
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
        AdmisiaApplicantInfo admisiaApplicantInfo=admisiaApplicantInfoRepository.findByCmsInfo_CmsIdAndRegistrationId(cmsId, registrationId);
        
        if(admisiaApplicantInfo==null) {
        	baseResponse.setMessage("No Info Found.");
        	baseResponse.setMessageType(0);
    		return baseResponse;	
        }
        
        if(admisiaApplicantInfo.getApplicantFeeStatus()==1) {
        	baseResponse.setMessage("Already Paid");
        	baseResponse.setMessageType(0);
    		return baseResponse;	
        }
        
        String storeId=admisiaApplicantInfo.getCmsInfo().getStoreId();
        String storePassword=admisiaApplicantInfo.getCmsInfo().getStorePasswd();
        
        String url=AdmisiaUtils.SSL_MARCHANT_TRANSACTION_VALIDATION_REQUEST+"?tran_id="+registrationId+"&store_id="+storeId+"&store_passwd="+storePassword+"&format=json";
		
        HttpResponse<String> response=null;
		try {
			response = Unirest.get(url)
					  .asString();
		} catch (UnirestException e) {
			baseResponse.setMessage("Exception Occured.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		JSONObject jsonObject=new JSONObject(response.getBody());
		
		JSONArray jarray=jsonObject.getJSONArray("element");
		
		for (int i = 0; i < jarray.length(); i++) {
          JSONObject nestedJsonObject = jarray.getJSONObject(i);
          String status=nestedJsonObject.getString("status");
          
          if(status.equals("VALID") || status.equals("VALIDATED")) {
        	  
        	  
        	String validatedOn=nestedJsonObject.getString("validated_on");
      		Date validatedDate=df.parse(validatedOn);
      		String trnId=nestedJsonObject.getString("tran_id");
      		String valId=nestedJsonObject.getString("val_id");
      		String trnDate=nestedJsonObject.getString("tran_date");
      		Date transactionDate=df.parse(trnDate);
      		Double amount=Double.parseDouble(nestedJsonObject.getString("amount"));
      		String bankTrnId=nestedJsonObject.getString("bank_tran_id");
      		Double storeAmount=Double.parseDouble(nestedJsonObject.getString("store_amount"));
      		String cardReferenceId="";//nestedJsonObject.getString("card_ref_id");
      		String cardType=nestedJsonObject.getString("card_type");
        	  
        	AdmisiaFeeTransactionLog transactionLog=new AdmisiaFeeTransactionLog();
      		transactionLog.setAdmisiaApplicantInfo(admisiaApplicantInfo);
      		transactionLog.setAmount(amount);
      		transactionLog.setBankTransactionId(bankTrnId);
      		transactionLog.setCardRefId(cardReferenceId);
      		transactionLog.setCardType(cardType);
      		transactionLog.setCmsInfo(admisiaApplicantInfo.getCmsInfo());
      		transactionLog.setLogDate(new Date());
      		transactionLog.setRegistrationId(trnId);
      		transactionLog.setStoreAmount(storeAmount);
      		transactionLog.setTransactionDate(transactionDate);
      		transactionLog.setTransactionTime(transactionDate);
      		transactionLog.setValId(valId);
      		transactionLog.setValidatedOn(validatedDate);
      		
      		admisiaFeeTransactionLogRepository.save(transactionLog);
      		changeApplicantStatus(admisiaApplicantInfo);
      		
      		baseResponse.setMessage("Transaction Successfully Covered.");
    		baseResponse.setMessageType(1);
    		
    		break;
        	  
        	  
          }
          
		}
		
        
		return baseResponse;
	}
  
  
  public void changeApplicantStatus(AdmisiaApplicantInfo admisiaApplicantInfo) {
  	
  	admisiaApplicantInfo.setApplicantFeeStatus(1);

		if (admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus() == 1
				&& admisiaApplicantInfo.getAdmisiaClassConfig().getAutoApproveStatus() == 0) {
			admisiaApplicantInfo.setApplicantStatus(AdmisiaApplicantStatusEnum.PENDING_FOR_ASSESSMENT.getCode());
		} else if (admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus() == 1
				&& admisiaApplicantInfo.getAdmisiaClassConfig().getAutoApproveStatus() == 1) {
			admisiaApplicantInfo.setApplicantStatus(AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode());
		} else if (admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus() == 0
				&& admisiaApplicantInfo.getAdmisiaClassConfig().getAutoApproveStatus() == 0) {
			admisiaApplicantInfo.setApplicantStatus(AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode());
		} else if (admisiaApplicantInfo.getAdmisiaClassConfig().getAdmissionExamStatus() == 0
				&& admisiaApplicantInfo.getAdmisiaClassConfig().getAutoApproveStatus() == 1) {
			admisiaApplicantInfo.setApplicantStatus(AdmisiaApplicantStatusEnum.APPROVED_FOR_ADMISSION.getCode());
			admisiaApplicantInfo.setStatusUpdateDate(new Date());
		}
  }

}
