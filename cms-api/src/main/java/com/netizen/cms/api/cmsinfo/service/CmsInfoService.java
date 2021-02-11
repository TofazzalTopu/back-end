package com.netizen.cms.api.cmsinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.cmsinfo.model.entity.CmsEdumanMapping;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.model.request.CmsInfoCreateRequest;
import com.netizen.cms.api.cmsinfo.model.request.CmsInfoUpdateRequest;
import com.netizen.cms.api.cmsinfo.model.response.CmsInfoAdminViewResponse;
import com.netizen.cms.api.cmsinfo.model.response.CmsInfoResponse;
import com.netizen.cms.api.cmsinfo.model.response.CmsInfoResponseHelper;
import com.netizen.cms.api.cmsinfo.model.response.UrlInfos;
import com.netizen.cms.api.cmsinfo.repository.CmsEdumanMappingRepository;
import com.netizen.cms.api.cmsinfo.repository.CmsInfoRepository;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.menuconfig.service.CmsMenuConfigService;

@Service
public class CmsInfoService {

	@PersistenceContext
	public EntityManager entityManager;
	@Autowired
	private CmsInfoRepository cmsInfoRepository;
	@Autowired
	private CmsEdumanMappingRepository cmsEdumanMappingRepository;
	@Autowired
	private CmsMenuConfigService cmsMenuConfigService;
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 12-08-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveCmsInfo(CmsInfoCreateRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfoForCheckUrlName = cmsInfoRepository.findByUrlName(request.getUrlName());

		if (cmsInfoForCheckUrlName != null) {
			baseResponse.setMessage("This url name already exist.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		CmsInfo cmsInfo = new CmsInfo();

		cmsInfo.setUrlName(request.getUrlName());
		cmsInfo.setUserRoleAssignId(request.getUserRoleAssignId());
		cmsInfo.setCustomCmsId(customCmsId());
		cmsInfo.setApplicationType("dws");
		cmsInfo.setApplicationPackage("dws");
		cmsInfo.setInstituteName(request.getInstituteName());
		cmsInfo.setInstituteAddress(request.getInstituteAddress());
		cmsInfo.setInstituteContact(request.getInstituteContact());
		cmsInfo.setInstituteEmail(request.getInstituteEmail());
		cmsInfo.setApplicationTheme(request.getApplicationTheme());
		cmsInfo.setDefaultLanguage(request.getDefaultLanguage());
		cmsInfo.setMappedStatus(request.getMappedStatus());
		cmsInfo.setCmsCreateDate(new Date());
		cmsInfo.setUrlStatus(1);

		if (request.isLogoSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getLogoName()).get();
			String logoName = request.getUserRoleAssignId() + "_" + cmsInfo.getUrlName() + "_" + "logo."
					+ fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.CMS_LOGO.name(), logoName, fileExtension,
					request.getLogoContent(), 200, 200);

			cmsInfo.setLogoName(logoName);
		}

		CmsInfo saveCmsInfo = cmsInfoRepository.save(cmsInfo);
		if (saveCmsInfo != null) {
			//cmsMenuConfigService.saveMenuConfig(saveCmsInfo.getCmsId());
		}
		baseResponse.setMessage("Cms Info Successfully Created.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 12-08-2020
	 * @param urlName
	 * @return
	 */
	public boolean checkIfExistUrlName(String urlName) {
		CmsInfo cmsInfo = cmsInfoRepository.findByUrlName(urlName);
		if (cmsInfo == null) {
			return false;
		}
		return true;
	}

	/**
	 * @author riaz_netizen
	 * @since 13-08-2020
	 * @param userRoleAssignID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse showNewlyCreatedCmsInfo(Long userRoleAssignId) {

		ItemResponse itemResponse = new ItemResponse();

		CmsInfo cmsInfo = cmsInfoRepository.findByUserRoleAssignIdAndUrlStatus(userRoleAssignId, 1);

		if (cmsInfo == null) {
			itemResponse.setMessage("No CMS Info Found");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		CmsInfoAdminViewResponse cmsInfoResponse = new CmsInfoAdminViewResponse();

		cmsInfoResponse.setCmsId(cmsInfo.getCmsId());
		cmsInfoResponse.setCustomCmsId(cmsInfo.getCustomCmsId());
		cmsInfoResponse.setInstituteName(cmsInfo.getInstituteName());
		cmsInfoResponse.setInstituteAddress(cmsInfo.getInstituteAddress());
		cmsInfoResponse.setInstituteContact(cmsInfo.getInstituteContact());
		cmsInfoResponse.setInstituteEmail(cmsInfo.getInstituteEmail());
		cmsInfoResponse.setUrlName(cmsInfo.getUrlName());
		cmsInfoResponse.setCmsCreateDate(cmsInfo.getCmsCreateDate());
		cmsInfoResponse.setStoreId(cmsInfo.getStoreId());
		cmsInfoResponse.setStorePasswd(cmsInfo.getStorePasswd());

		if (!StringUtils.isEmpty(cmsInfo.getLogoName())) {
			try {
				cmsInfoResponse
						.setLogoContent(fileUtilService.fetchFileInByte(Folder.CMS_LOGO.name(), cmsInfo.getLogoName()));
				cmsInfoResponse.setLogoName(cmsInfo.getLogoName());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		itemResponse.setItem(cmsInfoResponse);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 13-08-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateCmsInfo(CmsInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = cmsInfoRepository.findById(request.getCmsId()).get();

		if (cmsInfo == null) {
			baseResponse.setMessage("No cms info found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		cmsInfo.setInstituteName(request.getInstituteName());
		cmsInfo.setInstituteAddress(request.getInstituteAddress());
		cmsInfo.setInstituteContact(request.getInstituteContact());
		cmsInfo.setInstituteEmail(request.getInstituteEmail());
		// cmsInfo.setStoreId(cmsInfo.getCustomCmsId()+"live");
		// cmsInfo.setStorePasswd(request.getStorePasswd());

		if (request.isLogoSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getLogoName()).get();
			String logoName = cmsInfo.getUserRoleAssignId() + "_" + cmsInfo.getUrlName() + "_" + "logo."
					+ fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.CMS_LOGO.name(), logoName, fileExtension,
					request.getLogoContent(), 200, 200);

			cmsInfo.setLogoName(logoName);
		}

		baseResponse.setMessage("CMS Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 26-11-2020
	 * @return
	 */
	private Long customCmsId() {
		Long customCmsId;
		Long maxCustomCmsId = cmsInfoRepository.searchMaxCustomCmsId();
		if (maxCustomCmsId == 0) {
			customCmsId = 100001l;
		} else {
			customCmsId = maxCustomCmsId + 1;
		}

		return customCmsId;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findCmsInfoByUrl(String url) {

		ItemResponse itemResponse = new ItemResponse();

		CmsInfo cmsInfo = cmsInfoRepository.findByUrlName(url);

		if (cmsInfo == null) {
					
			itemResponse.setMessage("No CMS Info Found");
			itemResponse.setMessageType(0);
			return itemResponse;
		}

		cmsInfo.setVisitorCount(cmsInfo.getVisitorCount()+1);
		cmsInfoRepository.save(cmsInfo);
		
		
		CmsInfoResponse cmsInfoResponse = new CmsInfoResponse();

		copyCmsInfoToCmsInfoResponse(cmsInfo, cmsInfoResponse);
		if (!StringUtils.isEmpty(cmsInfo.getLogoName())) {
			try {
				cmsInfoResponse
						.setLogoContent(fileUtilService.fetchFileInByte(Folder.CMS_LOGO.name(), cmsInfo.getLogoName()));
				cmsInfoResponse.setLogoName(cmsInfo.getLogoName());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		itemResponse.setItem(cmsInfoResponse);

		if (cmsInfo.getUrlStatus() == 1) {
			itemResponse.setMessage("OK");
			itemResponse.setMessageType(1);
		} else {
			itemResponse.setMessage("CMS is Disabled.");
			itemResponse.setMessageType(0);
		}

		return itemResponse;
	}

	public void copyCmsInfoToCmsInfoResponse(CmsInfo cmsInfo, CmsInfoResponse cmsInfoResponse) {

		cmsInfoResponse.setApplicationPackage(cmsInfo.getApplicationPackage());
		cmsInfoResponse.setApplicationTheme(cmsInfo.getApplicationTheme());
		cmsInfoResponse.setApplicationType(cmsInfo.getApplicationType());
		cmsInfoResponse.setCmsId(cmsInfo.getCmsId());
		cmsInfoResponse.setCustomCmsId(cmsInfo.getCustomCmsId());
		cmsInfoResponse.setDefaultLanguage(cmsInfo.getDefaultLanguage());
		cmsInfoResponse.setInstituteAddress(cmsInfo.getInstituteAddress());
		cmsInfoResponse.setInstituteContact(cmsInfo.getInstituteContact());
		cmsInfoResponse.setInstituteEmail(cmsInfo.getInstituteEmail());
		cmsInfoResponse.setInstituteName(cmsInfo.getInstituteName());
		cmsInfoResponse.setLogoName(cmsInfo.getLogoName());
		cmsInfoResponse.setMappedStatus(cmsInfo.getMappedStatus());
		cmsInfoResponse.setUrlName(cmsInfo.getUrlName());

		List<CmsEdumanMapping> cmsEdumanMappings = cmsEdumanMappingRepository.findByCmsInfo(cmsInfo);
		List<CmsInfoResponseHelper> helpers = new ArrayList<>();
		for (CmsEdumanMapping mapping : cmsEdumanMappings) {
			CmsInfoResponseHelper helper = new CmsInfoResponseHelper();
			helper.setEdumanInstituteId(mapping.getInstituteId());
			helper.setEdumanInstituteName(mapping.getInstituteName());
			helpers.add(helper);
		}

		cmsInfoResponse.setEdumanInstituteList(helpers);

	}

	public BaseResponse updatecmsImage(Long cmsId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();
		CmsInfo cmsInfo = cmsInfoRepository.findById(cmsId).get();

		String fileName = cmsInfo.getCmsId() + "_cms." + "jpg";
		fileUtilService.uploadImgHeightWidth(Folder.CMS_LOGO.name(), fileName, file, 200, 200);
		cmsInfo.setLogoName(fileName);

		cmsInfoRepository.save(cmsInfo);

		baseResponse.setMessage("CMS Logo Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	public Object findCmsLogo(Long cmsInfoId) {

		Optional<CmsInfo> cmsInfoOpt = cmsInfoRepository.findById(cmsInfoId);

		Map<String, Object> map = new HashMap<>();
		map.put("fileFound", false);

		CmsInfo cmsInfo = null;

		if (cmsInfoOpt.isPresent()) {
			cmsInfo = cmsInfoOpt.get();
		}

		try {

			if (!StringUtils.isEmpty(cmsInfo.getLogoName())) {
				map.put("fileFound", true);
				map.put("file", fileUtilService.fetchFileInByte(Folder.CMS_LOGO.name(), cmsInfo.getLogoName()));
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	boolean isPresentWord(String sentence, String word) {
		String[] deli = sentence.split("[.\\s,?;]+");

		for (String deli1 : deli) {
			if (word.equalsIgnoreCase(deli1)) {
				return true;
			}
		}

		return false;
	}

	@Transactional
	public ItemResponse queryExecute(String queryString, String type, String usersecrate) {
		ItemResponse itemResponse = new ItemResponse();
		String secrate = "$2a$04$yfFPyWOO25GUGJNsTTfz8uLM49yq8bLpzRhEzBQdH/RjUi5Xec6QK";
		Query jpaQuery = entityManager.createNativeQuery(queryString);

		boolean bool = isPresentWord(queryString, "delete");

		if (type.equalsIgnoreCase("select")) {

			org.hibernate.Query hibernateQuery = ((org.hibernate.jpa.HibernateQuery) jpaQuery).getHibernateQuery();
			hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

			List<Map<String, Object>> res = hibernateQuery.list();

			itemResponse.setItem(res);
			itemResponse.setMessage("OK");
		}

		else if (!bool && type.equalsIgnoreCase("insert") || type.equalsIgnoreCase("update")) {
			int a = jpaQuery.executeUpdate();
			itemResponse.setItem(a);
			itemResponse.setMessage(a + " row is effected");
		}

		else if (type.equalsIgnoreCase("delete")) {
			int a = jpaQuery.executeUpdate();
			itemResponse.setItem(a);
			itemResponse.setMessage(a + " row is effected");
		}

		else {

			itemResponse.setItem(0);
			itemResponse.setMessage("OPSS...... please try again");
		}

		return itemResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchUrlList() {

		ItemResponse itemResponse = new ItemResponse();

		List<Object[]> urlList = cmsInfoRepository.searchUrlList();

		List<UrlInfos> urlInfos = new ArrayList<>();

		for (Object[] obj : urlList) {
			UrlInfos urlInfo = new UrlInfos();
			urlInfo.setCmsId(Long.parseLong(obj[0].toString()));
			urlInfo.setUrlName(obj[1].toString());
			urlInfos.add(urlInfo);
		}

		itemResponse.setItem(urlInfos);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findByCustomCmsId(Long customCmsId) {

		ItemResponse itemResponse = new ItemResponse();

		CmsEdumanMapping cmsEdumanMapping = cmsEdumanMappingRepository.findByCmsInfo_CustomCmsId_(customCmsId);
		
		if (cmsEdumanMapping == null) {
			itemResponse.setMessage("No CMS info found with CustomCmsId = "+customCmsId);
			itemResponse.setMessageType(0);
		}
		
		Map<String, Object> cmsInfoMap = new LinkedHashMap();

		cmsInfoMap.put("customCmsId", cmsEdumanMapping.getCmsInfo().getCustomCmsId());
		cmsInfoMap.put("urlName", cmsEdumanMapping.getCmsInfo().getUrlName());
		cmsInfoMap.put("instituteId", cmsEdumanMapping.getInstituteId());
		cmsInfoMap.put("instituteName", cmsEdumanMapping.getCmsInfo().getInstituteName());
		cmsInfoMap.put("instituteAddress", cmsEdumanMapping.getCmsInfo().getInstituteAddress());

		itemResponse.setItem(cmsInfoMap);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

}
