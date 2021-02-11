package com.netizen.cms.api.jasper.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netizen.cms.api.admisia.common.AdmisiaUtils;
import com.netizen.cms.api.admisia.model.entity.AdmisiaApplicantInfo;
import com.netizen.cms.api.admisia.model.entity.AdmisiaCoreConfig;
import com.netizen.cms.api.admisia.model.response.AdmitCardView;
import com.netizen.cms.api.admisia.repository.AdmisiaApplicantInfoRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaCoreConfigRepository;
import com.netizen.cms.api.common.enums.AdmisiaApplicantStatusEnum;
import com.netizen.cms.api.common.enums.AdmisiaAssessmentStatusEnum;
import com.netizen.cms.api.common.enums.AdmisiaFeeStatusEnum;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.jasper.utils.JasperUtils;

@Service
public class AdmisiaJasperDownloadService {
	
	public  static final Logger logger = Logger.getLogger(AdmisiaJasperDownloadService.class);
	
	
	@Autowired
	public AdmisiaCoreConfigRepository admisiaCoreConfigRepository;
	
	@Autowired
	public AdmisiaApplicantInfoRepository admisiaApplicantInfoRepository;
	
	@Autowired
	public JasperUtils jasperUtils;
	
	public static final String ADMIT_CARD_PATH = "jasper/";
	
	public static final String IMAGE_PATH = AdmisiaUtils.getImagePath(Folder.MEMBER.name());
	
	public ItemResponse downloadAdmitCard(Long cmsId, List<String> registrationIds) {
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		AdmisiaCoreConfig admisiaCoreConfig = admisiaCoreConfigRepository.findByCmsInfo_CmsId(cmsId);
		

		List<AdmisiaApplicantInfo> applicantInfos = admisiaApplicantInfoRepository
				.findByCmsInfo_CmsIdAndAdmisiaClassConfig_AdmissionExamStatusAndApplicantStatusAndApplicantFeeStatusAndAcademicYearAndRegistrationIdInOrderByRollNo(
						cmsId, AdmisiaAssessmentStatusEnum.ASSESSMENT_STATUS_YES.getCode(),
						AdmisiaApplicantStatusEnum.PENDING_FOR_ADMISSION.getCode(),
						AdmisiaFeeStatusEnum.FEE_STATUS_PAID.getCode(), admisiaCoreConfig.getCurrentAdmissionYear(),
						registrationIds);
		List<AdmitCardView> admitCardViews = new ArrayList<>();
		for (AdmisiaApplicantInfo applicantInfo : applicantInfos) {

			AdmitCardView admitCardView = new AdmitCardView();
			dataFillIntoAdmitCardView(admitCardView, applicantInfo);
			admitCardViews.add(admitCardView);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("INSTITUTE_NAME", admisiaCoreConfig.getCmsInfo().getInstituteName());
		map.put("INSTITUTE_ADDRESS", admisiaCoreConfig.getCmsInfo().getInstituteAddress());
		map.put("INSTITUTE_LOGO", admisiaCoreConfig.getCmsInfo().getLogoName());
		map.put("LOGO_PATH", Folder.CMS_LOGO.name());
		map.put("APPLICANT_PHOTO_PATH",IMAGE_PATH);
		map.put("printDate", df.format(new Date()));
		
		map.put("SIGNATURE_PATH", IMAGE_PATH);
		
		logger.info("+++++++++++++++ Folder.MEMBER.name() ==============="+IMAGE_PATH);
		
		logger.info("+++++++++++++++ Folder.MEMBER.name() ==============="+IMAGE_PATH);
		
		try {
			
			logger.info("+++++++++++++++ Signature Logo Name ==============="+applicantInfos.get(0).getAdmisiaClassConfig().getSignatureImage());
			
			map.put("SIGNATURE_NAME", applicantInfos.get(0).getAdmisiaClassConfig().getSignatureImage());
			map.put("SIGNATURE_TITLE", applicantInfos.get(0).getAdmisiaClassConfig().getSignatureTitle());			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		

		String template = ADMIT_CARD_PATH + "AdmitCard.jasper";


		try {
			jasperUtils.jasperPrintWithList(admitCardViews, map, template,"Admit_Card");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessage("Admit Cards are downloaded");
		itemResponse.setMessageType(1);
		return itemResponse;

	}
	
	
	
	public void dataFillIntoAdmitCardView(AdmitCardView admitCardView,AdmisiaApplicantInfo applicantInfo) {
		
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		DateFormat df2=new SimpleDateFormat("HH:mm a");
		
		
		admitCardView.setAcademicYear(applicantInfo.getAcademicYear()+"");
		admitCardView.setApplicantName(applicantInfo.getApplicantName());
		admitCardView.setClassName(applicantInfo.getAdmisiaClassConfig().getClassInfo().getClassName());
		admitCardView.setExamCenter(applicantInfo.getAdmisiaClassConfig().getExamCenterName());
		admitCardView.setExamDate(df.format(applicantInfo.getAdmisiaClassConfig().getAdmissionExamDate()));
		admitCardView.setExamTime(df2.format(applicantInfo.getAdmisiaClassConfig().getAdmissionExamTime()));
		admitCardView.setFatherName(applicantInfo.getFatherName());
		admitCardView.setMotherName(applicantInfo.getMotherName());
		admitCardView.setGender(applicantInfo.getGender());
		admitCardView.setGroupName(applicantInfo.getAdmisiaClassConfig().getGroupInfo().getGroupName());
		admitCardView.setMobileNo(applicantInfo.getMobileNo());
		admitCardView.setPhotoName(applicantInfo.getApplicantPhoto());
		logger.info("+++++++++++++++++ Applicant Photo Name +++++++++++++++ "+applicantInfo.getApplicantPhoto());
		admitCardView.setRegistrationNo(applicantInfo.getRegistrationId());
		admitCardView.setReligion(applicantInfo.getReligion());
		admitCardView.setRollNo(applicantInfo.getRollNo());

		
		
	}
	

}
