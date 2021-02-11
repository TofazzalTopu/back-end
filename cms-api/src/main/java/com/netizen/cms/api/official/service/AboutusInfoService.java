package com.netizen.cms.api.official.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.repository.CmsInfoRepository;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.official.model.entity.AboutusInfo;
import com.netizen.cms.api.official.model.request.AboutusInfoSaveRequest;
import com.netizen.cms.api.official.model.request.AboutusInfoUpdateRequest;
import com.netizen.cms.api.official.model.response.AboutusInfoViewResponse;
import com.netizen.cms.api.official.repository.AboutusInfoRepository;

@Service
public class AboutusInfoService {

	
	@Autowired
	private AboutusInfoRepository aboutusInfoRepository;
	
	@Autowired
	private CmsInfoRepository cmsInfoRepository;
	
	@Autowired
	private FileUtilService fileUtilService;
	
	public BaseResponse saveAboutusInfo(AboutusInfoSaveRequest request){
		
		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo=cmsInfoRepository.getOne(request.getCmsId());
		
		if(cmsInfo==null) {
			baseResponse.setMessage("Cms Info Not Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		AboutusInfo aboutusInfo = AboutusInfo.builder().build();
		aboutusInfo.setAboutusDetails(request.getAboutusDetails());
		aboutusInfo.setAboutusNote(request.getAboutusNote());
		aboutusInfo.setAboutusType(request.getAboutusType());
		aboutusInfo.setCmsInfo(cmsInfo);
		

		if(request.isFileSaveOrEditable()) {
			
			String[] aboutusTypes = request.getAboutusType().split(" ");
			String aboutusType = "";
			for(String type : aboutusTypes) {
				aboutusType += type + "_";
			}			
			
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = request.getCmsId() + "_" + aboutusType + "aboutus." + fileExtension;					
			fileUtilService.writeFileToPath(Folder.ABOUTUS.name(), request.getFileContent(), fileName);
			aboutusInfo.setImageName(fileName);
		}
	
		aboutusInfoRepository.save(aboutusInfo);
		
		baseResponse.setMessage("Aboutus information successfully saved");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemResponse findAboutusInfos(Long cmsId) {

		List<AboutusInfo> aboutuss = aboutusInfoRepository.findByCmsInfo_CmsId(cmsId);
		
		List<AboutusInfoViewResponse> aboutusInfoResponses = aboutuss.stream()
				.map(aboutus -> copyAboutusInfoToAboutusInfoViewResponse(aboutus)).collect(Collectors.toList());
		

		ItemResponse response = new ItemResponse();
		response.setItem(aboutusInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
	
	public ItemResponse<AboutusInfoViewResponse> findAboutusInfoByType(Long cmsId, String typeName) {

		AboutusInfo aboutus = aboutusInfoRepository.findByCmsInfo_CmsIdAndAboutusType(cmsId, typeName);
	
		ItemResponse<AboutusInfoViewResponse> itemResponse = new ItemResponse<>();
		
		if(aboutus == null ) {
			
			itemResponse.setMessage("No Aboutus found of type " + typeName);
			itemResponse.setMessageType(0);
			return itemResponse;
		}
		

		AboutusInfoViewResponse response = copyAboutusInfoToAboutusInfoViewResponse(aboutus); 
//		try {
//			response.setAboutusImg(fileUtilService.fetchFileInByte(Folder.ABOUTUS.name(), aboutus.getImageName()));
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
		
		itemResponse.setItem(response);
		itemResponse.setMessageType(1);
		itemResponse.setMessage("OK");

		return itemResponse;
	}
	
  public BaseResponse updateAboutusInfo(AboutusInfoUpdateRequest request){
	
	    BaseResponse baseResponse = new BaseResponse();
	    
	    CmsInfo cmsInfo=cmsInfoRepository.getOne(request.getCmsId());
		
		AboutusInfo aboutusInfo = aboutusInfoRepository.findByCmsInfoAndAboutusId(cmsInfo, request.getAboutusId());
		
		if(aboutusInfo==null) {
				baseResponse.setMessage("No Aboutus found to update.");
				baseResponse.setMessageType(0);
				return baseResponse;
		}

		
		aboutusInfo.setAboutusDetails(request.getAboutusDetails());
		aboutusInfo.setAboutusNote(request.getAboutusNote());
		
		if(request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = aboutusInfo.getCmsInfo().getCmsId()+ "_"  + aboutusInfo.getAboutusType()+ "_" + "aboutus." + fileExtension;					
			fileUtilService.writeFileToPath(Folder.ABOUTUS.name(), request.getFileContent(), fileName);
			aboutusInfo.setImageName(fileName);
		}
		aboutusInfoRepository.save(aboutusInfo);
		

		baseResponse.setMessage("Aboutus information successfully update");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	public BaseResponse deleteAboutusInfo(Long aboutusId) {
		
		BaseResponse baseResponse = new BaseResponse();

		AboutusInfo aboutusInfo = aboutusInfoRepository.getOne(aboutusId);

		if (aboutusInfo == null) {
			baseResponse.setMessage("No Aboutus found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		aboutusInfoRepository.delete(aboutusInfo);
		fileUtilService.deleteFile(Folder.ABOUTUS.name(), aboutusInfo.getImageName());

		BaseResponse response = new BaseResponse();
		response.setMessage("Aboutus Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}

	
	public AboutusInfoViewResponse copyAboutusInfoToAboutusInfoViewResponse(AboutusInfo aboutusInfo) {
		
		AboutusInfoViewResponse response = AboutusInfoViewResponse.builder().build();
		response.setAboutusDetails(aboutusInfo.getAboutusDetails());
		response.setAboutusId(aboutusInfo.getAboutusId());
		response.setAboutusNote(aboutusInfo.getAboutusNote());
		response.setAboutusType(aboutusInfo.getAboutusType());
		
		//BeanUtils.copyProperties(aboutusInfo, aboutusInfoViewResponse);
		
		if (!StringUtils.isEmpty(aboutusInfo.getImageName())) {
			try {
				response.setFileContent(fileUtilService.fetchFileInByte(Folder.ABOUTUS.name(), aboutusInfo.getImageName()));
				response.setFileName(aboutusInfo.getImageName());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return response;		
		
	}
	
	
	public BaseResponse updateAboutusImage(Long aboutusInfoId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();
		Optional<AboutusInfo> aboutusInfoOpt = aboutusInfoRepository.findById(aboutusInfoId);
		AboutusInfo aboutusInfo = aboutusInfoOpt.get();

		if (!aboutusInfoOpt.isPresent()) {
			baseResponse.setMessage("No Aboutus Info Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		String fileName = aboutusInfo.getCmsInfo().getCmsId()  + "_aboutus"	+ ".jpg";
		fileUtilService.fileUpload(Folder.ABOUTUS.name(), fileName, file);
		aboutusInfo.setImageName(fileName);
		
		aboutusInfoRepository.save(aboutusInfo);

		baseResponse.setMessage("Aboutus Info Image Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	

}
