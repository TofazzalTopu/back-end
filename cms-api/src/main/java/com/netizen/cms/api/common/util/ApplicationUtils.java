package com.netizen.cms.api.common.util;

import java.beans.FeatureDescriptor;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.multipart.MultipartFile;

public class ApplicationUtils {
	
	public static final SimpleDateFormat LOCAL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String filePath(String imageFolder) {
		Properties prop = new Properties();
		InputStream inputStream = ApplicationUtils.class.getClassLoader()
				.getResourceAsStream("imagepath/image-path.properties");
		try {
			prop.load(inputStream);
			return prop.getProperty(imageFolder);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
	}

	public static long getDiffOfDays(Date fromDate, Date toDate) {
		Timestamp fromDateToTimestamp = new Timestamp(fromDate.getTime());
		long diff = toDate.getTime() - fromDateToTimestamp.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
}
