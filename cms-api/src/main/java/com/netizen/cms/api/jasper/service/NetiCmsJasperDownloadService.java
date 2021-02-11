package com.netizen.cms.api.jasper.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.jasper.utils.JasperUtils;

@Service
public class NetiCmsJasperDownloadService {
	
	@Autowired
	public JasperUtils jasperUtils;
	
	public static final String ADMIT_CARD_PATH = "jasper/";
	
	 public ItemResponse findApplicantInfo(Long cmsId, String registrationId) throws Exception {
	        String url = "https://api.netizendev.com:2096/public/applicant/personal-info/by-registrationId?registrationId=registrationId&cmsId=cmsId";

	       // ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	       // String filepath = servletContext.getRealPath("/");
	        Map params = new HashMap();
	       // params.put("ContextPath", filepath);ADMIT_CARD_PATH + "AdmitCard.jasper"
//	        String fileName = "EmBillCopy_" + edumanInstID + "_" + billingId;
	        params.put("ContextPath", ADMIT_CARD_PATH + "ApplicantInfo.jasper");

	        String fileName = "ApplicantInfo";
	       // JasperReportPrinter reportPrinter = new JasperReportPrinter();
	        jasperUtils.printReport(fileName, url, params, "ApplicantInfo.jasper");
	        
	        ItemResponse itemResponse = new ItemResponse();
			itemResponse.setMessage("Admit Cards are downloaded");
			itemResponse.setMessageType(1);
			return itemResponse;
	    }

}
