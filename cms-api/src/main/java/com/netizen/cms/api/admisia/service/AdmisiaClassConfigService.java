package com.netizen.cms.api.admisia.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.admisia.model.entity.AdmisiaClassConfig;
import com.netizen.cms.api.admisia.model.entity.AdmisiaCoreConfig;
import com.netizen.cms.api.admisia.model.request.AdmisiaClassConfigSaveRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaClassConfigUpdateRequest;
import com.netizen.cms.api.admisia.model.response.AdmisiaClassConfigPublicView;
import com.netizen.cms.api.admisia.model.response.AdmisiaClassConfigViewResponse;
import com.netizen.cms.api.admisia.repository.AdmisiaClassConfigRepository;
import com.netizen.cms.api.admisia.repository.AdmisiaCoreConfigRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.repository.CmsInfoRepository;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;
import com.netizen.cms.api.common.util.FileUtilService;

@Service
public class AdmisiaClassConfigService {

	@Autowired
	private AdmisiaClassConfigRepository admisiaClassConfigRepository;

	@Autowired
	private AdmisiaCoreConfigRepository admisiaCoreConfigRepository;

	@Autowired
	private FileUtilService fileUtilService;

	@Autowired
	private CmsInfoRepository cmsInfoRepository;

	/**
	 * @author riaz_netizen
	 * @since 30-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveClassConfig(AdmisiaClassConfigSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		AdmisiaClassConfig classConfig = admisiaClassConfigRepository
				.findByCmsInfo_CmsIdAndClassInfo_ClassIdAndGroupInfo_groupId(request.getCmsId(), request.getClassId(),
						request.getGroupId());

		if (classConfig != null) {
			baseResponse.setMessageType(0);
			baseResponse.setMessage("This class and group Already Configed.");
			return baseResponse;

		}

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		AdmisiaCoreConfig admisiaCoreConfig = admisiaCoreConfigRepository.findByCmsInfo_CmsId(request.getCmsId());

		ClassInfo classInfo = ClassInfo.builder().classId(request.getClassId()).build();

		GroupInfo groupInfo = GroupInfo.builder().groupId(request.getGroupId()).build();

		AdmisiaClassConfig admisiaClassConfig = new AdmisiaClassConfig();

		admisiaClassConfig.setCmsInfo(cmsInfo);
		admisiaClassConfig.setAdmisiaCoreConfig(admisiaCoreConfig);
		admisiaClassConfig.setClassInfo(classInfo);
		admisiaClassConfig.setGroupInfo(groupInfo);
		admisiaClassConfig.setClassConfigSerial(request.getClassConfigSerial());
		admisiaClassConfig.setApplicationStartDate(request.getApplicationStartDate());
		admisiaClassConfig.setApplicationEndDate(request.getApplicationEndDate());
		admisiaClassConfig.setApplicantLimit(request.getApplicantLimit());
		admisiaClassConfig.setApplicationFee(request.getApplicationFee());
		admisiaClassConfig.setServiceCharge(admisiaCoreConfig.getServiceCharge());
		admisiaClassConfig.setTotalFee(request.getApplicationFee() + admisiaCoreConfig.getServiceCharge());
		admisiaClassConfig.setPrevExamInfoRequiredStatus(request.getPrevExamInfoRequiredStatus());
		admisiaClassConfig.setAdmissionExamStatus(request.getAdmissionExamStatus());

		if (request.getAdmissionExamStatus() == 1) {

			DateFormat df = new SimpleDateFormat("HH:mm");
			Date examTime = null;

			try {
				examTime = df.parse(request.getAdmissionExamTime());
			} catch (ParseException e) {
				baseResponse.setMessage("Date Time Format not Correct");
				baseResponse.setMessageType(0);
				return baseResponse;
			}

			admisiaClassConfig.setAdmissionExamDate(request.getAdmissionExamDate());
			admisiaClassConfig.setAdmissionExamTime(examTime);
			admisiaClassConfig.setAdmissionExamInstruction(request.getAdmissionExamInstruction());
			admisiaClassConfig.setExamCenterName(request.getExamCenterName());
		}

		admisiaClassConfig.setAutoApproveStatus(request.getAutoApproveStatus());
		admisiaClassConfig.setEnableStatus(1);
		admisiaClassConfig.setSignatureTitle(request.getSignatureTitle());

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = request.getCmsId() + request.getClassId() + request.getGroupId() + "_" + "signature."
					+ fileExtension;
//			fileUtilService.writeFileToPath(Folder.ADMISIA_CLASS_CONFIG.name(), request.getFileContent(),
//					fileName);

			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
			admisiaClassConfig.setSignatureImage(fileName);
		}

		admisiaClassConfigRepository.save(admisiaClassConfig);

		baseResponse.setMessage("Admisia Class config Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 30-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateClassConfig(AdmisiaClassConfigUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		AdmisiaClassConfig admisiaClassConfig = admisiaClassConfigRepository.findById(request.getClassConfigId()).get();

		admisiaClassConfig.setClassConfigSerial(request.getClassConfigSerial());
		admisiaClassConfig.setApplicationStartDate(request.getApplicationStartDate());
		admisiaClassConfig.setApplicationEndDate(request.getApplicationEndDate());
		admisiaClassConfig.setApplicantLimit(request.getApplicantLimit());
		admisiaClassConfig.setApplicationFee(request.getApplicationFee());
		admisiaClassConfig.setTotalFee(request.getApplicationFee() + admisiaClassConfig.getServiceCharge());
		admisiaClassConfig.setPrevExamInfoRequiredStatus(request.getPrevExamInfoRequiredStatus());
		admisiaClassConfig.setAdmissionExamStatus(request.getAdmissionExamStatus());
		if (request.getAdmissionExamStatus() == 1) {

			DateFormat df = new SimpleDateFormat("HH:mm");
			Date examTime = null;

			try {
				examTime = df.parse(request.getAdmissionExamTime());
			} catch (ParseException e) {
				baseResponse.setMessage("Date Time Format not Correct");
				baseResponse.setMessageType(0);
				return baseResponse;
			}

			admisiaClassConfig.setAdmissionExamDate(request.getAdmissionExamDate());
			admisiaClassConfig.setAdmissionExamTime(examTime);
			admisiaClassConfig.setAdmissionExamInstruction(request.getAdmissionExamInstruction());
			admisiaClassConfig.setExamCenterName(request.getExamCenterName());
		}

		admisiaClassConfig.setAutoApproveStatus(request.getAutoApproveStatus());
		admisiaClassConfig.setEnableStatus(request.getEnableStatus());
		admisiaClassConfig.setSignatureTitle(request.getSignatureTitle());

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = admisiaClassConfig.getCmsInfo().getCmsId()
					+ admisiaClassConfig.getClassInfo().getClassId() + admisiaClassConfig.getGroupInfo().getGroupId()
					+ "_" + "signature." + fileExtension;
//			fileUtilService.writeFileToPath(Folder.ADMISIA_CLASS_CONFIG.name(), request.getFileContent(),
//					fileName);

			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
			admisiaClassConfig.setSignatureImage(fileName);
		}

		baseResponse.setMessage("Admisia Class config Info Successfully updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 30-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searClassConfigList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<AdmisiaClassConfig> classConfigs = admisiaClassConfigRepository
				.findByCmsInfo_CmsIdOrderByClassConfigSerial(cmsId);

		List<AdmisiaClassConfigViewResponse> responses = new ArrayList<>();

		for (AdmisiaClassConfig info : classConfigs) {
			AdmisiaClassConfigViewResponse response = new AdmisiaClassConfigViewResponse();
			copyClassConfigToClassConfigViewResponse(info, response);

			if (info.getSignatureImage() != null) {
				try {
//					response.setFileContent(fileUtilService
//							.fetchFileInByte(Folder.ADMISIA_CLASS_CONFIG.name(), info.getSignatureImage()));

					response.setFileContent(
							fileUtilService.fetchFileInByte(Folder.MEMBER.name(), info.getSignatureImage()));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public void copyClassConfigToClassConfigViewResponse(AdmisiaClassConfig classConfig,
			AdmisiaClassConfigViewResponse classConfigViewResponse) {
		// DateFormat df=new SimpleDateFormat("hh:mm a");
		classConfigViewResponse.setClassConfigId(classConfig.getClassConfigId());
		classConfigViewResponse.setClassConfigSerial(classConfig.getClassConfigSerial());
		classConfigViewResponse.setApplicationStartDate(classConfig.getApplicationStartDate());
		classConfigViewResponse.setApplicationEndDate(classConfig.getApplicationEndDate());
		classConfigViewResponse.setApplicantLimit(classConfig.getApplicantLimit());
		classConfigViewResponse.setApplicationFee(classConfig.getApplicationFee());
		classConfigViewResponse.setServiceCharge(classConfig.getServiceCharge());
		classConfigViewResponse.setTotalFee(classConfig.getTotalFee());
		classConfigViewResponse.setEnableStatus(classConfig.getEnableStatus());
		classConfigViewResponse.setPrevExamInfoRequiredStatus(classConfig.getPrevExamInfoRequiredStatus());
		classConfigViewResponse.setAutoApproveStatus(classConfig.getAutoApproveStatus());
		classConfigViewResponse.setAdmissionExamStatus(classConfig.getAdmissionExamStatus());
		classConfigViewResponse.setAdmissionExamDate(classConfig.getAdmissionExamDate());
		classConfigViewResponse.setAdmissionExamTime(classConfig.getAdmissionExamTime());
//		if(classConfig.getAdmissionExamTime()!=null) {
//			classConfigViewResponse.setAdmissionExamTime(df.format(classConfig.getAdmissionExamTime()));	
//		}
		classConfigViewResponse.setAdmissionExamInstruction(classConfig.getAdmissionExamInstruction());
		classConfigViewResponse.setExamCenterName(classConfig.getExamCenterName());
		classConfigViewResponse.setClassName(classConfig.getClassInfo().getClassName());
		classConfigViewResponse.setGroupName(classConfig.getGroupInfo().getGroupName());
		classConfigViewResponse.setSignatureTitle(classConfig.getSignatureTitle());
		classConfigViewResponse.setFileName(classConfig.getSignatureImage());

	}

	/**
	 * @author riaz_netizen
	 * @since 10-06-2020
	 * @param cmsId
	 * @return
	 */
	public Double findServiceChargeAmount(Long cmsId) {
		Double amount = admisiaCoreConfigRepository.getServiceCharge(cmsId);
		return amount;
	}

	public ItemResponse admisiaClassConfigPublicView(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = null;
				
	   try {
			dateWithoutTime = df1.parse(df1.format(new Date()));

		} catch (ParseException e) {

			itemResponse.setMessage("Date Parsing Exception");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		Optional<CmsInfo> cmsInfo = cmsInfoRepository.findById(cmsId);
		if (!cmsInfo.isPresent()) {
			itemResponse.setMessage("No Cms Info Found.");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		List<AdmisiaClassConfig> admisiaClassConfigs = admisiaClassConfigRepository
				.findByCmsInfo_CmsIdAndApplicationStartDateLessThanEqualAndApplicationEndDateGreaterThanEqualAndEnableStatusOrderByClassConfigSerial(
						cmsInfo.get().getCmsId(), dateWithoutTime, dateWithoutTime,1);

		List<AdmisiaClassConfigPublicView> views = new ArrayList<>();
		for (AdmisiaClassConfig cnf : admisiaClassConfigs) {
			AdmisiaClassConfigPublicView view = new AdmisiaClassConfigPublicView();
			view.setClassConfigId(cnf.getClassConfigId());
			view.setCurrentAcademicYear(cnf.getAdmisiaCoreConfig().getCurrentAdmissionYear());
			view.setClassName(cnf.getClassInfo().getClassName());
			view.setGroupName(cnf.getGroupInfo().getGroupName());
			view.setApplicationStartDate(df2.format(cnf.getApplicationStartDate()));
			view.setApplicationEndDate(df2.format(cnf.getApplicationEndDate()));
			Long diff = ApplicationUtils.getDiffOfDays(dateWithoutTime, cnf.getApplicationEndDate()) + 1;
			view.setLeftDays(diff);
			view.setApplicationFee(cnf.getApplicationFee());
			view.setServiceCharge(cnf.getServiceCharge());
			view.setTotalFee(cnf.getTotalFee());
			view.setPrevExamInfoRequiredStatus(cnf.getPrevExamInfoRequiredStatus());
			view.setApplicantLimit(cnf.getApplicantLimit());
			views.add(view);
		}

		itemResponse.setItem(views);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;

	}

	/**
	 * @author riaz_netizen
	 * @since 18-10-2020
	 * @param cmsId
	 * @param classId
	 * @param groupId
	 * @return
	 */

	public ItemResponse findClassConfigInfo(Long cmsId, Long classId, Long groupId) {

		ItemResponse itemResponse = new ItemResponse();

		AdmisiaClassConfig classConfig = admisiaClassConfigRepository
				.findByCmsInfo_CmsIdAndClassInfo_ClassIdAndGroupInfo_groupId(cmsId, classId, groupId);

		Map<String, Object> map = new LinkedHashMap();

		map.put("autoApprovedStatus", classConfig.getAutoApproveStatus());
		map.put("admissionExamStatus", classConfig.getAdmissionExamStatus());
		map.put("applicantLimit", classConfig.getApplicantLimit());

		itemResponse.setItem(map);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 25-10-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findClassConfigInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();
		List<Map<String, Object>> configsMap = new ArrayList<>();

		List<AdmisiaClassConfig> admisiaClassConfigs = admisiaClassConfigRepository.findByCmsInfo_CmsIdAndEnableStatusOrderByClassInfo_ClassIdAsc(cmsId, 1);
		
		for (AdmisiaClassConfig obj : admisiaClassConfigs) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("classConfigId", obj.getClassConfigId());
			map.put("class", obj.getClassInfo().getClassName());
			map.put("group", obj.getGroupInfo().getGroupName());
			map.put("applicantLimit", obj.getApplicantLimit());
			map.put("autoApprovedStatus", obj.getAutoApproveStatus());
			map.put("admissionExamStatus", obj.getAdmissionExamStatus());			

			configsMap.add(map);
		}		

		itemResponse.setItem(configsMap);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

}
