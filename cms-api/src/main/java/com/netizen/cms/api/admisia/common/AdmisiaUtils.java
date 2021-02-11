package com.netizen.cms.api.admisia.common;

import java.io.InputStream;
import java.util.Properties;


public class AdmisiaUtils {

//	public static final String HOST_URL="https://api.netizendev.com:2096";
	public static final String HOST_URL="https://api.neticms.com";
	
	//public static final String HOST_URL="http://localhost:8080";
	
	// Prod
//	public static final String SSL_STORE_ID="Admisialive";
//	public static final String SSL_STORE_PASSWORD="5FB3D88AE79C637742";

	// Dev
//	public static final String SSL_STORE_ID="test_disbursement";
//	public static final String SSL_STORE_PASSWORD="test_disbursement@ssl";
	
// 	Dev
//	public static final String SSL_SESSION_ID_REQUEST="https://sandbox.sslcommerz.com/gwprocess/v4/api.php";
//	public static final String SSL_VALIDATION_REQUEST="https://sandbox.sslcommerz.com/validator/api/validationserverAPI.php";
//	public static final String SSL_MARCHANT_TRANSACTION_VALIDATION_REQUEST="https://sandbox.sslcommerz.com/validator/api/merchantTransIDvalidationAPI.php";

	
//  Prod		
	public static final String SSL_SESSION_ID_REQUEST="https://securepay.sslcommerz.com/gwprocess/v4/api.php";
	public static final String SSL_VALIDATION_REQUEST="https://securepay.sslcommerz.com/validator/api/validationserverAPI.php";
	public static final String SSL_MARCHANT_TRANSACTION_VALIDATION_REQUEST="https://securepay.sslcommerz.com/validator/api/merchantTransIDvalidationAPI.php";
	
	
	public static final String SSL_SUCCESS_URL=HOST_URL+"/public/admisia/application-fee/success/transaction";
	public static final String SSL_CANCEL_URL=HOST_URL+"/public/admisia/application-fee/cancel";
	public static final String SSL_FAILED_URL=HOST_URL+"/public/admisia/application-fee/failed";

	
	
	
	public static String getImagePath(String imageFolder) {
		Properties prop = new Properties();
		InputStream inputStream = AdmisiaUtils.class.getClassLoader()
				.getResourceAsStream("imagepath/image-path.properties");
		try {
			prop.load(inputStream);
			return prop.getProperty(imageFolder);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
}
