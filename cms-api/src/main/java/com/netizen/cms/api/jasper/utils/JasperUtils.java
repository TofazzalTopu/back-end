package com.netizen.cms.api.jasper.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;

@Component
public class JasperUtils {
	
	public static final Logger logger = Logger.getLogger(JasperUtils.class);

//	String jasperfile = "jasper/AdmitCard.jasper";
	public boolean jasperPrintWithList(List<?> list, Map<String, Object> paramsMap, String jasperFilePath,String downLoadFileName) throws Exception {

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jasperFilePath);

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, paramsMap,
				new JRBeanCollectionDataSource(list));

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


		HttpServletResponse httpServletResponse = ((ServletRequestAttributes) requestAttributes).getResponse();

		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + downLoadFileName + ".pdf");

		try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {

			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			logger.info("Download complete.");

			servletOutputStream.flush();

			return true;
		}
	}
	
	
	 public void printReport(String filName, String url, Map params, String jrXmlFileName){
	        try {
	            StringBuilder stringBuilder = new StringBuilder();
	            stringBuilder.append(filName);
	            InputStream is = new URL(url).openStream();
	            JRDataSource dataSource = new JsonDataSource(is);
	            String jrXmlSource = "/jasper/"+jrXmlFileName;
	          //  String jrXmlSource = "com/netiworld/netiman/jasper/"+jrXmlFileName;
	            InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream(jrXmlSource);
	            JasperPrint jp = JasperFillManager.fillReport(inputStream, params, dataSource);
	            
	            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	    		HttpServletResponse httpServletResponse = ((ServletRequestAttributes) requestAttributes).getResponse();

	           // HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
	             //       .getCurrentInstance().getExternalContext().getResponse();
	            httpServletResponse.addHeader("Content-disposition",
	                    "attachment; filename=" + stringBuilder + ".pdf");
	            //FacesContext.getCurrentInstance().responseComplete();
	            try (ServletOutputStream servletOutputStream = httpServletResponse
	                    .getOutputStream()) {
	                JasperExportManager.exportReportToPdfStream(jp,
	                        servletOutputStream);
	                System.out.println("All done the report is done");
	                servletOutputStream.flush();
	                //FacesContext.getCurrentInstance().responseComplete();
	            }
	            
	        } catch (IOException | JRException e) {
	        	logger.error("ERROR for : " + filName+" couse of -> "+e);
	        	logger.fatal("FATAL for : " + filName+" couse of -> "+e);
	        	logger.debug("DEBUG for : " + filName+" couse of -> "+e);
	        }
	    }
	

	
	
}
