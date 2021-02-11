package com.netizen.cms.api.admisia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netizen.cms.api.admisia.model.entity.AdmisiaCoreConfig;
import com.netizen.cms.api.admisia.model.request.AdmisiaCoreConfigSaveRequest;
import com.netizen.cms.api.admisia.model.request.AdmisiaCoreConfigUpdateRequest;
import com.netizen.cms.api.admisia.model.request.ServiceChargeUpdateRequest;
import com.netizen.cms.api.admisia.model.response.AdmisiaCoreConfigViewResponse;
import com.netizen.cms.api.admisia.repository.AdmisiaCoreConfigRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;

@Service
public class AdmisiaCoreConfigService {

	@Autowired
	private AdmisiaCoreConfigRepository coreConfigRepository;
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveCoreConfig(AdmisiaCoreConfigSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		AdmisiaCoreConfig coreConfig = new AdmisiaCoreConfig();
		coreConfig.setCmsInfo(cmsInfo);
		coreConfig.setCurrentAdmissionYear(request.getCurrentAdmissionYear());
		coreConfig.setCircularTitle(request.getCircularTitle());
		coreConfig.setConfigDate(new Date());
		coreConfig.setServiceCharge(30);

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = coreConfig.getCmsInfo().getCmsId() + "_" + "circular." + fileExtension;
			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
//			fileUtilService.writeFileToPath(Folder.ADMISIA_CORE_CONFIG.name(), request.getFileContent(),
//					fileName);
			coreConfig.setCircularImage(fileName);
		}

		coreConfigRepository.save(coreConfig);

		baseResponse.setMessage("Admisia Core config Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateCoreConfig(AdmisiaCoreConfigUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		AdmisiaCoreConfig coreConfig = coreConfigRepository.findById(request.getCoreConfigId()).get();

		if (coreConfig == null) {
			baseResponse.setMessage("No file found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		coreConfig.setCurrentAdmissionYear(request.getCurrentAdmissionYear());
		coreConfig.setCircularTitle(request.getCircularTitle());

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = coreConfig.getCmsInfo().getCmsId() + "_" + "circular." + fileExtension;
			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
//			fileUtilService.writeFileToPath(Folder.ADMISIA_CORE_CONFIG.name(), request.getFileContent(),
//					fileName);
			coreConfig.setCircularImage(fileName);
		}

		coreConfigRepository.save(coreConfig);

		baseResponse.setMessage("Admisia Core Config Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 28-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findCoreConfigByCmsId(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		AdmisiaCoreConfig coreConfig = coreConfigRepository.findByCmsInfo_CmsId(cmsId);

		if (coreConfig == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Admisia Not Configured");
			return itemResponse;
		}

		AdmisiaCoreConfigViewResponse coreConfigViewResponse = copyCoreConfigToViewResponse(coreConfig);

		if (coreConfig.getCircularImage() != null) {
			try {
//				coreConfigViewResponse.setFileContent(
//						fileUtilService.fetchFileInByte(Folder.ADMISIA_CORE_CONFIG.name(), coreConfig.getCircularImage()));

				coreConfigViewResponse.setFileContent(
						fileUtilService.fetchFileInByte(Folder.MEMBER.name(), coreConfig.getCircularImage()));
				coreConfigViewResponse.setFileName(coreConfig.getCircularImage());
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(coreConfigViewResponse);

		return itemResponse;
	}

	public AdmisiaCoreConfigViewResponse copyCoreConfigToViewResponse(AdmisiaCoreConfig admisiaCoreConfig) {

		AdmisiaCoreConfigViewResponse admisiaCoreConfigViewResponse = AdmisiaCoreConfigViewResponse.builder().build();

		BeanUtils.copyProperties(admisiaCoreConfig, admisiaCoreConfigViewResponse);

		return admisiaCoreConfigViewResponse;
	}

	
	/**
	 * @author riaz_netizen
	 * @since 10-07-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findAllConfigList() {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);

		List<AdmisiaCoreConfig> coreConfigs = coreConfigRepository.findAll();

		if (coreConfigs == null) {
			itemResponse.setMessageType(0);
			itemResponse.setMessage("Admisia Configuration list is empty");
			return itemResponse;
		}
		List<Map<String, Object>> maps = new ArrayList<>();

		for (AdmisiaCoreConfig info : coreConfigs) {
			Map<String, Object> map = new LinkedHashMap<>();

			map.put("coreConfigId", info.getCoreConfigId());
			map.put("cmsId", info.getCmsInfo().getCmsId());
			map.put("urlName", info.getCmsInfo().getUrlName());
			map.put("instituteName", info.getCmsInfo().getInstituteName());
			map.put("instituteAddress", info.getCmsInfo().getInstituteAddress());
			map.put("instituteContact", info.getCmsInfo().getInstituteContact());
			map.put("academicYear", info.getCurrentAdmissionYear());
			map.put("serviceCharge", info.getServiceCharge());
			map.put("configDate", info.getConfigDate());
			
			maps.add(map);
		}

		itemResponse.setMessageType(1);
		itemResponse.setMessage("Ok");
		itemResponse.setItem(maps);

		return itemResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 10-08-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateServiceCharge(ServiceChargeUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		AdmisiaCoreConfig coreConfig = coreConfigRepository.findByCoreConfigIdAndCmsInfo_CmsId(request.getCoreConfigId(), request.getCmsId());

		if (coreConfig == null) {
			baseResponse.setMessage("No config id found to update service charge.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		coreConfig.setServiceCharge(request.getServiceCharge());
		

		coreConfigRepository.save(coreConfig);

		baseResponse.setMessage("Service Charge Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}
}
