package com.netizen.cms.api.admisia.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.netizen.cms.api.admisia.service.AdmisiaApplicationFeeService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
public class AdmisiaApplicationFeeController {
	
	
	@Autowired
	public AdmisiaApplicationFeeService admisiaApplicationFeeService;
	
	
	@GetMapping(value = "/admisia/application-fee/pay")
	public RedirectView redirectToSSL(@RequestParam String registrationId) throws UnirestException{
				
		RedirectView redirectView = new RedirectView();
		
		ItemResponse itemResponse=admisiaApplicationFeeService.redirectToSSLGatewayPaymentPage(registrationId);
		
		if(itemResponse.getMessageType()==1) {
			redirectView.setUrl(itemResponse.getItem().toString());
		    return redirectView;
			
		}
		
		redirectView.setUrl("http://www.yahoo.com");
	    return redirectView;
	}
	
	
		@PostMapping(value = "/public/admisia/application-fee/success/transaction")
		public String admisiaSuccessTransaction(HttpServletRequest request) throws UnsupportedEncodingException, ParseException{
			
			String status=request.getParameter("status");
	
			ItemResponse itemResponse=new ItemResponse<>();
			
	         if(status.equals("VALID")) {
	        	
	        	 itemResponse=admisiaApplicationFeeService.validatePayment(request);
	         }
	
			return loadSuccessfulPagePath(itemResponse.getMessage());
			
		}
		
		
		@GetMapping(value = "/admisia/application-fee/enquiry")
		public ResponseEntity<BaseResponse> admisiaMarchantTransactionValidation(Long cmsId,String registrationId) throws ParseException{
			BaseResponse baseResponse=admisiaApplicationFeeService.admisiaApplicationFeeEnquiry(cmsId, registrationId);
	        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
			
		}
	
	
	
		@PostMapping(value = "/public/admisia/application-fee/cancel")
		public String admisiaCancelTransaction(){
			
			return loadCancelPagePath();
			
		}
		
		
		@PostMapping(value = "/public/admisia/application-fee/failed")
		public String admisiaFailedTransaction(){
			return loadTrnFailedPagePath();
		}

	    public String loadSuccessfulPagePath(String message){
	        String page="<html>"
	                + "<head><title>Admisia Payment || Netizen IT Limited</title></head>"
	                + "<body>" 
	                + "<div style='display: flex;flex-direction: column;justify-content: center;align-items: center;text-align: center;min-height: 100vh;'>"
	                + "<h1>Congratulations! Your Transaction Successfully Done</h1>"
	                + "<h2>"+message+"</h2>"
	                + "<br>" 
	                + "<b>Powered By <a href='https://www.netizenbd.com/'>Netizen IT Limited</a></b>" 
	                + "</div>" 
	                + "</body>" 
	                + "</html>";
	        return page;
	    }
	    
	    
	    
	    public String loadCancelPagePath(){
	        String page="<html>"
	                + "<head><title>Admisia Payment || Netizen IT Limited</title></head>"
	                + "<body>" 
	                + "<div style='display: flex;flex-direction: column;justify-content: center;align-items: center;text-align: center;min-height: 100vh;'>"
	                + "<h1>You Have Canceled Your Transaction</h1>"
	                + "<br>" 
	                + "<b>Powered By <a href='https://www.netizenbd.com/'>Netizen IT Limited</a></b>" 
	                + "</div>" 
	                + "</body>" 
	                + "</html>";
	        return page;
	    }
	    
	    
	    public String loadTrnFailedPagePath(){
	        String page="<html>"
	                + "<head><title>Admisia Payment || Netizen IT Limited</title></head>"
	                + "<body>" 
	                + "<div style='display: flex;flex-direction: column;justify-content: center;align-items: center;text-align: center;min-height: 100vh;'>"
	                + "<h1>Sorry! Your Transaction is Failed.</h1>"
	                + "<br>" 
	                + "<b>Powered By <a href='https://www.netizenbd.com/'>Netizen IT Limited</a></b>" 
	                + "</div>" 
	                + "</body>" 
	                + "</html>";
	        return page;
	    }

}
