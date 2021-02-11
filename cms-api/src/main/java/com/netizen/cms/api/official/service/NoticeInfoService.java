package com.netizen.cms.api.official.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.official.model.entity.NoticeInfo;
import com.netizen.cms.api.official.model.request.NoticeInfoSaveRequest;
import com.netizen.cms.api.official.model.request.NoticeInfoUpdateRequest;
import com.netizen.cms.api.official.model.response.NoticeInfoViewEnableResponse;
import com.netizen.cms.api.official.model.response.NoticeInfoViewExpiredResponse;
import com.netizen.cms.api.official.repository.NoticeInfoRepository;

@Service
public class NoticeInfoService {

	@Autowired
	private NoticeInfoRepository noticeInfoRepository;
	
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveNoticeInfo(NoticeInfoSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();
		
		NoticeInfo noticeInfo = new NoticeInfo();
		noticeInfo.setCmsInfo(cmsInfo);
		noticeInfo.setCreatedBy(request.getUserName());
		noticeInfo.setNoticeSerial(request.getNoticeSerial());
		noticeInfo.setNoticeTitle(request.getNoticeTitle());
		noticeInfo.setNoticeDetails(request.getNoticeDetails());
		noticeInfo.setNoticeIssueDate(request.getNoticeIssueDate());
		noticeInfo.setNoticeExpiryDate(request.getNoticeExpiryDate());
		noticeInfo.setNoticeStatus(1);
		noticeInfo.setNoticeCreateDate(new Date());		
		
		NoticeInfo savedNotice =  noticeInfoRepository.save(noticeInfo);
		
		if(request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = noticeInfo.getCmsInfo().getCmsId() + "_" + savedNotice.getNoticeId()+ "_" + "notice." + fileExtension;
			fileUtilService.writeFileToPath(Folder.NOTICE.name(), request.getFileContent(), fileName);
			savedNotice.setNoticeFileName(fileName);
		}
		

		baseResponse.setMessage("Notice Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 28-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateNoticeInfo(NoticeInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();
	

		NoticeInfo noticeInfo = noticeInfoRepository.findByNoticeIdAndCmsInfo(request.getNoticeId(), cmsInfo);

		if (noticeInfo == null) {
			baseResponse.setMessage("No notice found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		noticeInfo.setNoticeSerial(request.getNoticeSerial());
		noticeInfo.setNoticeTitle(request.getNoticeTitle());
		noticeInfo.setNoticeDetails(request.getNoticeDetails());
		noticeInfo.setNoticeIssueDate(request.getNoticeIssueDate());
		noticeInfo.setNoticeExpiryDate(request.getNoticeExpiryDate());
		noticeInfo.setNoticeStatus(request.getNoticeStatus());
		noticeInfo.setModifiedBy(request.getUserName());
		noticeInfo.setModifiedDate(new Date());
		
		if(request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = noticeInfo.getCmsInfo().getCmsId() + "_" + noticeInfo.getNoticeId()+ "_" + "notice." + fileExtension;					
			fileUtilService.writeFileToPath(Folder.NOTICE.name(), request.getFileContent(), fileName);
			noticeInfo.setNoticeFileName(fileName);
		}

		baseResponse.setMessage("Notice Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}
	
	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param noticeId
	 * @param file
	 * @return
	 */
	public BaseResponse updateSingleNoticeFile(Long noticeId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();

		NoticeInfo noticeInfo = noticeInfoRepository.findById(noticeId).get();

		if (noticeInfo == null) {
			baseResponse.setMessage("No Notice Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		String[] fileExtension = fileUtilService.provideFileExtension(file);
		String imageName = noticeInfo.getCmsInfo().getCmsId() + "_" + noticeInfo.getNoticeId() + "_" + "notice." + fileExtension[1];
		fileUtilService.fileUpload(Folder.NOTICE.name(), imageName, file);
		noticeInfo.setNoticeFileName(imageName);
		noticeInfoRepository.save(noticeInfo);

		baseResponse.setMessage("Notice File Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	

	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findEnableNoticeInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<NoticeInfo> noticeInfos =noticeInfoRepository.findByCmsInfo_CmsIdAndNoticeStatusAndNoticeExpiryDateGreaterThanEqualAndNoticeIssueDateLessThanEqualOrderByNoticeSerialAsc(cmsId, 1, new Date(),new Date());

		List<NoticeInfoViewEnableResponse> responses = new ArrayList<>();

		for (NoticeInfo info : noticeInfos) {
			NoticeInfoViewEnableResponse response = new NoticeInfoViewEnableResponse();
			copyNoticeInfoToNoticeInfoViewEnableResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 10-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findEnableNoticeInfoListForAdmin(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<NoticeInfo> noticeInfos =noticeInfoRepository.findByCmsInfo_CmsIdAndNoticeStatusAndNoticeExpiryDateGreaterThanEqualOrderByNoticeSerialAsc(cmsId, 1, new Date());

		List<NoticeInfoViewEnableResponse> responses = new ArrayList<>();

		for (NoticeInfo info : noticeInfos) {
			NoticeInfoViewEnableResponse response = new NoticeInfoViewEnableResponse();
			copyNoticeInfoToNoticeInfoViewEnableResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public void copyNoticeInfoToNoticeInfoViewEnableResponse(NoticeInfo noticeInfo,
			NoticeInfoViewEnableResponse noticeViewEnableInfo) {	
		
		noticeViewEnableInfo.setNoticeId(noticeInfo.getNoticeId());
		noticeViewEnableInfo.setNoticeSerial(noticeInfo.getNoticeSerial());
		noticeViewEnableInfo.setNoticeTitle(noticeInfo.getNoticeTitle());
		noticeViewEnableInfo.setNoticeDetails(noticeInfo.getNoticeDetails());
		noticeViewEnableInfo.setNoticeStatus(noticeInfo.getNoticeStatus());
		noticeViewEnableInfo.setNoticeIssueDate(noticeInfo.getNoticeIssueDate());
		noticeViewEnableInfo.setNoticeExpiryDate(noticeInfo.getNoticeExpiryDate());
		noticeViewEnableInfo.setNoticeFileName(noticeInfo.getNoticeFileName());
		noticeViewEnableInfo.setRemainExpireation(ApplicationUtils.getDiffOfDays(new Date(), noticeInfo.getNoticeExpiryDate()));

	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 29-06-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findExpiredDisableOrEnableNoticeInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(cmsId).build();
		

		List<Object[]> list =noticeInfoRepository.findExpiredDisableNotice(cmsInfo.getCmsId());

		List<NoticeInfoViewExpiredResponse> responses = new ArrayList<>();

		for (Object[] obj : list) {
			NoticeInfoViewExpiredResponse response = new NoticeInfoViewExpiredResponse();
			copyNoticeInfoToNoticeInfoViewExpireResponse(obj, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}
	
	
	public void copyNoticeInfoToNoticeInfoViewExpireResponse(Object[] obj,
			NoticeInfoViewExpiredResponse expiredResponse) {	
		
		expiredResponse.setNoticeId(Long.parseLong(obj[0].toString()));
		expiredResponse.setNoticeSerial(Integer.parseInt(obj[1].toString()));
		
		if (obj[2] != null) {
		expiredResponse.setNoticeFileName(obj[2].toString());	
		}
		
		expiredResponse.setNoticeTitle(obj[3].toString());
		expiredResponse.setNoticeDetails(obj[4].toString());		
		expiredResponse.setNoticeIssueDate((Date)obj[5]);		
		expiredResponse.setNoticeExpiryDate((Date)obj[6]);
		expiredResponse.setNoticeStatus(Integer.parseInt(obj[7].toString()));
		expiredResponse.setExpireStatus(obj[8].toString());

	}
	
	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param noticeId
	 * @return
	 */
	public Map<String, Object> findNoticeFile(Long noticeId) { 
		
		Optional<NoticeInfo> noticeInfoOpt = noticeInfoRepository.findById(noticeId);
		
		Map<String, Object> map = new HashMap<>();
		map.put("fileFound", false);

		NoticeInfo noticeInfo = null;

		if (noticeInfoOpt.isPresent()) {
			noticeInfo = noticeInfoOpt.get();
		}
			
		try {
			
			if(! StringUtils.isEmpty(noticeInfo.getNoticeFileName())) {		
				map.put("fileFound", true);
				map.put("file", fileUtilService.fetchFileInByte(Folder.NOTICE.name(),  noticeInfo.getNoticeFileName()));	
				return map;
			}
						
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
		return map;
	}


}
