package com.netizen.cms.api.official.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.official.model.entity.SpeechInfo;
import com.netizen.cms.api.official.model.request.SpeechInfoSaveRequest;
import com.netizen.cms.api.official.model.request.SpeechInfoUpdateRequest;
import com.netizen.cms.api.official.model.response.SpeechInfoViewResponse;
import com.netizen.cms.api.official.repository.SpeechInfoRepository;

@Service
public class SpeechInfoService {
	@Autowired
	private SpeechInfoRepository speechInfoRepository;

	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveSpeechInfo(SpeechInfoSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		SpeechInfo speechInfo = new SpeechInfo();
		speechInfo.setCmsInfo(cmsInfo);
		speechInfo.setSpeakerName(request.getSpeakerName());
		speechInfo.setSpeakerDesignation(request.getSpeakerDesignation());
		speechInfo.setSpeakerMobile(request.getSpeakerMobile());
		speechInfo.setSpeakerEmail(request.getSpeakerEmail());
		speechInfo.setSpeechDetails(request.getSpeechDetails());
		speechInfo.setSpeakerFacebookLinke(request.getSpeakerFacebookLinke());
		speechInfo.setSpeakerLinkedinLinke(request.getSpeakerLinkedinLinke());
		speechInfo.setSpeakerTwitterLinke(request.getSpeakerTwitterLinke());
		speechInfo.setSpeechSerial(request.getSpeechSerial());
		speechInfo.setSpeechStatus(1);
		speechInfo.setSpeechDate(new Date());
		speechInfo.setWelcomeSpeechStatus(request.getWelcomeSpeechStatus()); 

		SpeechInfo savedSpeechInfo = speechInfoRepository.save(speechInfo);

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String imageName = speechInfo.getCmsInfo().getCmsId() + "_" + savedSpeechInfo.getSpeechId() + "_"
					+ speechInfo.getSpeakerMobile() + "_" + "speech." + fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.SPEAKER_PHOTO.name(), imageName, fileExtension,
					request.getFileContent(), 200, 200);

			speechInfo.setSpeakerImgName(imageName);
		}

		baseResponse.setMessage("Speech Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateSpeechInfo(SpeechInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		SpeechInfo speechInfo = speechInfoRepository.findBySpeechIdAndCmsInfo(request.getSpeechId(), cmsInfo);

		if (speechInfo == null) {
			baseResponse.setMessage("No speech found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		speechInfo.setSpeakerName(request.getSpeakerName());
		speechInfo.setSpeakerDesignation(request.getSpeakerDesignation());
		speechInfo.setSpeakerMobile(request.getSpeakerMobile());
		speechInfo.setSpeakerEmail(request.getSpeakerEmail());
		speechInfo.setSpeechDetails(request.getSpeechDetails());
		speechInfo.setSpeakerFacebookLinke(request.getSpeakerFacebookLinke());
		speechInfo.setSpeakerLinkedinLinke(request.getSpeakerLinkedinLinke());
		speechInfo.setSpeakerTwitterLinke(request.getSpeakerTwitterLinke());
		speechInfo.setSpeechSerial(request.getSpeechSerial());
		speechInfo.setWelcomeSpeechStatus(request.getWelcomeSpeechStatus()); 

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String imageName = speechInfo.getCmsInfo().getCmsId() + "_" + speechInfo.getSpeechId() + "_"
					+ speechInfo.getSpeakerMobile() + "_" + "speech." + fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.SPEAKER_PHOTO.name(), imageName, fileExtension,
					request.getFileContent(), 200, 200);

			speechInfo.setSpeakerImgName(imageName);
		}

		baseResponse.setMessage("Speech Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param speechId
	 * @param file
	 * @return
	 *//*
		 * public BaseResponse updateSingleSpeakerPhoto(Long speechId, MultipartFile
		 * file) {
		 * 
		 * BaseResponse baseResponse = new BaseResponse();
		 * 
		 * SpeechInfo speechInfo = speechInfoRepository.findById(speechId).get();
		 * 
		 * if (speechInfo == null) { baseResponse.setMessage("No Speech Found");
		 * baseResponse.setMessageType(0); return baseResponse; }
		 * 
		 * String[] fileExtension = fileUtilService.provideFileExtension(file); String
		 * imageName = speechInfo.getCmsInfo().getCmsId() + "_" +
		 * speechInfo.getSpeechId() + "_" + speechInfo.getSpeakerMobile() + "_" +
		 * "speech." +fileExtension[1];
		 * fileUtilService.uploadImgHeightWidth(Folder.SPEAKER_PHOTO.name(), imageName,
		 * file, 200, 200); speechInfo.setSpeakerImgName(imageName);
		 * speechInfoRepository.save(speechInfo);
		 * 
		 * baseResponse.setMessage("Speech Image Successfully Uploaded.");
		 * baseResponse.setMessageType(1); return baseResponse; }
		 */
	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param speechId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteSpeechInfo(Long speechId) {

		BaseResponse baseResponse = new BaseResponse();

		SpeechInfo speechInfo = speechInfoRepository.findById(speechId).get();

		if (speechInfo == null) {
			baseResponse.setMessage("No speech found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		speechInfoRepository.delete(speechInfo);
		fileUtilService.deleteFile(Folder.SPEAKER_PHOTO.name(), speechInfo.getSpeakerImgName());

		baseResponse.setMessage("Speech Info Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 25/06/2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse showSpeechInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<SpeechInfo> speechInfos = speechInfoRepository
				.findByCmsInfoOrderBySpeechSerialAsc(CmsInfo.builder().cmsId(cmsId).build());

		List<SpeechInfoViewResponse> responses = new ArrayList<>();

		for (SpeechInfo info : speechInfos) {

			SpeechInfoViewResponse speechViewInfo = new SpeechInfoViewResponse();
			copySpeechInfoToSpeechInfoViewResponse(info, speechViewInfo);

			if (!StringUtils.isEmpty(info.getSpeakerImgName())) {
				try {
					speechViewInfo.setFileContent(fileUtilService.fetchFileInByte(Folder.SPEAKER_PHOTO.name(),
							info.getSpeakerImgName()));
					speechViewInfo.setFileName(info.getSpeakerImgName()); 
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			responses.add(speechViewInfo);

		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 25-06-2020
	 * @param speechInfo
	 * @param speechViewInfo
	 */
	public SpeechInfoViewResponse copySpeechInfoToSpeechInfoViewResponse(SpeechInfo speechInfo,
			SpeechInfoViewResponse speechViewInfo) {

		speechViewInfo.setSpeechId(speechInfo.getSpeechId());
		speechViewInfo.setSpeakerName(speechInfo.getSpeakerName());
		speechViewInfo.setSpeakerDesignation(speechInfo.getSpeakerDesignation());
		speechViewInfo.setSpeakerMobile(speechInfo.getSpeakerMobile());
		speechViewInfo.setSpeakerEmail(speechInfo.getSpeakerEmail());
		speechViewInfo.setSpeechDetails(speechInfo.getSpeechDetails());
		speechViewInfo.setSpeakerFacebookLinke(speechInfo.getSpeakerFacebookLinke());
		speechViewInfo.setSpeakerLinkedinLinke(speechInfo.getSpeakerLinkedinLinke());
		speechViewInfo.setSpeakerTwitterLinke(speechInfo.getSpeakerTwitterLinke());
		speechViewInfo.setSpeechSerial(speechInfo.getSpeechSerial());
		speechViewInfo.setSpeechStatus(speechInfo.getSpeechStatus());
		speechViewInfo.setSpeechDate(speechInfo.getSpeechDate());
		speechViewInfo.setSpeakerImgName(speechInfo.getSpeakerImgName());
		speechViewInfo.setWelcomeSpeechStatus(speechInfo.getWelcomeSpeechStatus());

		return speechViewInfo;
	}

	public ItemResponse<?> findWelcomeSpeech(Long cmsId) {

		List<SpeechInfo> speechInfos = speechInfoRepository
				.findByCmsInfo_CmsIdAndSpeechStatusAndWelcomeSpeechStatusOrderBySpeechSerial(cmsId, 1, 1);

		ItemResponse<List<SpeechInfoViewResponse>> response = new ItemResponse<>();

		if (speechInfos.isEmpty()) {
			response.setMessage("No Welcome Speech found");
			response.setMessageType(0);
			return response;
		}
		
		List<SpeechInfoViewResponse> responses = new ArrayList<>();
		
		for (SpeechInfo info : speechInfos) {

			SpeechInfoViewResponse speechViewInfo = new SpeechInfoViewResponse();
			copySpeechInfoToSpeechInfoViewResponse(info, speechViewInfo);

			if (!StringUtils.isEmpty(info.getSpeakerImgName())) {
				try {
					speechViewInfo.setFileContent(fileUtilService.fetchFileInByte(Folder.SPEAKER_PHOTO.name(),
							info.getSpeakerImgName()));
					speechViewInfo.setFileName(info.getSpeakerImgName());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			responses.add(speechViewInfo);

		}

		response.setItem(responses);

		return response;
	}

	public SpeechInfoViewResponse copySpeechInfoToSpeechInfoViewResponseForClient(SpeechInfo speechInfo) {
		SpeechInfoViewResponse speechViewInfo = new SpeechInfoViewResponse();

		speechViewInfo.setSpeechId(speechInfo.getSpeechId());
		speechViewInfo.setSpeakerName(speechInfo.getSpeakerName());
		speechViewInfo.setSpeakerDesignation(speechInfo.getSpeakerDesignation());
		speechViewInfo.setSpeakerMobile(speechInfo.getSpeakerMobile());
		speechViewInfo.setSpeakerEmail(speechInfo.getSpeakerEmail());
		speechViewInfo.setSpeechDetails(speechInfo.getSpeechDetails());
		speechViewInfo.setSpeakerFacebookLinke(speechInfo.getSpeakerFacebookLinke());
		speechViewInfo.setSpeakerLinkedinLinke(speechInfo.getSpeakerLinkedinLinke());
		speechViewInfo.setSpeakerTwitterLinke(speechInfo.getSpeakerTwitterLinke());
		speechViewInfo.setSpeechSerial(speechInfo.getSpeechSerial());
		speechViewInfo.setSpeechStatus(speechInfo.getSpeechStatus());
		speechViewInfo.setSpeechDate(speechInfo.getSpeechDate());
		speechViewInfo.setSpeakerImgName(speechInfo.getSpeakerImgName());

		return speechViewInfo;
	}

}
