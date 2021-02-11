package com.netizen.cms.api.academic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.academic.model.entity.DressInfo;
import com.netizen.cms.api.academic.model.request.DressInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.DressInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.DressInfoViewResponse;
import com.netizen.cms.api.academic.repository.DressInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;


@Service
public class DressInfoService {
	
	@Autowired
	private DressInfoRepository dressInfoRepository;
	@Autowired
	private FileUtilService fileUtilService;
	
	/**
	 * @author Zakir (modify-riaz_netizen )
	 * @since 18-08-2020
	 * @param dressInfoSaveRequest
	 * @return
	 */
	@Transactional
	public BaseResponse saveDressInfo(DressInfoSaveRequest dressInfoSaveRequest){
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		
		CmsInfo cmsInfo = CmsInfo.builder().cmsId(dressInfoSaveRequest.getCmsId()).build();
		
		DressInfo dressInfo=new DressInfo();
		
		dressInfo.setCmsInfo(cmsInfo);
		dressInfo.setDressSerial(dressInfoSaveRequest.getDressSerial());
		dressInfo.setClassRange(dressInfoSaveRequest.getClassRange());
		dressInfo.setGender(dressInfoSaveRequest.getGender());
		dressInfo.setDressDetails(dressInfoSaveRequest.getDressDetails());			
		
		DressInfo savedDressInfo = dressInfoRepository.save(dressInfo);
		
		if (dressInfoSaveRequest.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(dressInfoSaveRequest.getFileName()).get();
			String imageName = dressInfoSaveRequest.getCmsId() + "_" + savedDressInfo.getDressId() + "_" + "dress." + fileExtension;
			fileUtilService.writeFileToPath(Folder.DRESS.name(), dressInfoSaveRequest.getFileContent(), imageName);
			savedDressInfo.setDressImageName(imageName);
		}
		
		baseResponse.setMessage("Dress information successfully saved");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	

	public ItemResponse<List<DressInfoViewResponse>> findDressInfos(Long cmsId) {

		List<DressInfo> dresses = dressInfoRepository.findByCmsInfo_CmsIdOrderByDressSerial(cmsId);
		
		List<DressInfoViewResponse> groupInfoResponses = dresses.stream()
				.map(dress -> copyDressInfoToDressInfoViewRespons(dress)).collect(Collectors.toList());

		ItemResponse<List<DressInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(groupInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
	
	public ItemResponse<List<DressInfoViewResponse>> findDressInfosWithImage(Long cmsId) {

		List<DressInfo> dresses = dressInfoRepository.findByCmsInfo_CmsIdOrderByDressSerial(cmsId);
		
		List<DressInfoViewResponse> groupInfoResponses = dresses.stream()
				.map(dress -> copyDressInfoToDressInfoViewResponsWithImage(dress)).collect(Collectors.toList());

		ItemResponse<List<DressInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(groupInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
	
	/**
	 * @author Zakir (modify-riaz_netizen )
	 * @since 18-08-2020
	 * @param dressInfoUpdateRequest
	 * @return
	 */
	@Transactional
	public BaseResponse updateDressInfo(DressInfoUpdateRequest dressInfoUpdateRequest) {

		DressInfo dressInfo = dressInfoRepository.findById(dressInfoUpdateRequest.getDressId()).get();	

		BaseResponse baseResponse = new BaseResponse();

		if (dressInfo == null) {
			baseResponse.setMessage("No Dress found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		dressInfo.setGender(dressInfoUpdateRequest.getGender());
		dressInfo.setClassRange(dressInfoUpdateRequest.getClassRange());
		dressInfo.setDressDetails(dressInfoUpdateRequest.getDressDetails());
	
		if (dressInfoUpdateRequest.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(dressInfoUpdateRequest.getFileName()).get();
			String imageName = dressInfo.getCmsInfo().getCmsId() + "_" + dressInfo.getDressId() + "_" + "dress." + fileExtension;
			fileUtilService.writeFileToPath(Folder.DRESS.name(), dressInfoUpdateRequest.getFileContent(), imageName);
			dressInfo.setDressImageName(imageName);
		}
		
		dressInfoRepository.save(dressInfo);
		
		baseResponse.setMessage("Dress Info Successfully Updated");
		baseResponse.setMessageType(1);

		return baseResponse;
	}
	
	public BaseResponse deleteDressInfo(Long dressId) {

		Optional<DressInfo> dressInfoOpt = dressInfoRepository.findById(dressId);
		
		DressInfo dressInfo = null;
		
		if(dressInfoOpt.isPresent()) {
			dressInfo = dressInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (dressInfo == null) {
			baseResponse.setMessage("No Dress found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}


		dressInfoRepository.delete(dressInfo);
		fileUtilService.deleteFile(Folder.DRESS.name(), dressInfo.getDressImageName());

		BaseResponse response = new BaseResponse();
		response.setMessage("Dress Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}
		
	public DressInfoViewResponse copyDressInfoToDressInfoViewRespons(DressInfo dressInfo) {
		
		DressInfoViewResponse dressInfoViewResponse = DressInfoViewResponse.builder().build();
		BeanUtils.copyProperties(dressInfo, dressInfoViewResponse);
		return dressInfoViewResponse;	
	}
	
	public DressInfoViewResponse copyDressInfoToDressInfoViewResponsWithImage(DressInfo dressInfo) {
		
		DressInfoViewResponse dressInfoViewResponse = copyDressInfoToDressInfoViewRespons(dressInfo);
		
		if(! StringUtils.isEmpty(dressInfo.getDressImageName())) {		
			try {
				dressInfoViewResponse.setFileContent(fileUtilService.fetchFileInByte(Folder.DRESS.name(), dressInfo.getDressImageName()));
				dressInfoViewResponse.setFileName(dressInfo.getDressImageName()); 
			} catch (Exception e) {					
				System.out.println(e.getMessage());
			}  
		}	
		
		
		
		return dressInfoViewResponse;	
	}
	
	
	public BaseResponse updateDressImage(Long dressId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();
		Optional<DressInfo> dressInfoOpt = dressInfoRepository.findById(dressId);
		DressInfo dressInfo = dressInfoOpt.get();

		if (!dressInfoOpt.isPresent()) {
			baseResponse.setMessage("No Syllabus Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		String fileName = dressInfo.getCmsInfo().getCmsId()  + "_dress"	+ ".jpg";
		fileUtilService.fileUpload(Folder.DRESS.name(), fileName, file);
		dressInfo.setDressImageName(fileName);
		
		dressInfoRepository.save(dressInfo);

		baseResponse.setMessage("Dress Image Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}



	public Object findDressImage(Long dressInfoId) {
		
		Optional<DressInfo> dressInfoOpt = dressInfoRepository.findById(dressInfoId);
		
		Map<String, Object> map = new HashMap<>();
		map.put("fileFound", false);

		DressInfo dressInfo = null;

		if (dressInfoOpt.isPresent()) {
			dressInfo = dressInfoOpt.get();
		}
			
		try {
			
			if(! StringUtils.isEmpty(dressInfo.getDressImageName())) {		
				map.put("fileFound", true);
				map.put("file", fileUtilService.fetchFileInByte(Folder.DRESS.name(),  dressInfo.getDressImageName()));	
				return map;
			}
						
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	/**
	 * @author riaz_netizen
	 * @since 08-07-2020
	 * @param gender
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findGendeWiseDressListForClient(String gender,Long cmsId) {
		
		ItemResponse itemResponse=new ItemResponse();

		List<DressInfo> dressInfos=dressInfoRepository.findByGenderAndCmsInfo_CmsIdOrderByDressSerialAsc(gender, cmsId);
		
		List<DressInfoViewResponse> responses=new ArrayList<>();
		
		for(DressInfo info : dressInfos) {
			DressInfoViewResponse response=new DressInfoViewResponse();
			
			response.setDressId(info.getDressId());
			response.setClassRange(info.getClassRange());
			response.setDressDetails(info.getDressDetails());
			response.setFileName(info.getDressImageName());
			
			
			if(! StringUtils.isEmpty(info.getDressImageName())) {		
				try {
					response.setFileContent(fileUtilService.fetchFileInByte(Folder.DRESS.name(), info.getDressImageName()));					
				} catch (Exception e) {					
					System.out.println(e.getMessage());
				}  
			}			
			
			responses.add(response);			
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		
		return itemResponse;
	}


}
