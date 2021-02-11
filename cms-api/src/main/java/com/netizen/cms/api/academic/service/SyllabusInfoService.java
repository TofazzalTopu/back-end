package com.netizen.cms.api.academic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.academic.model.entity.SyllabusInfo;
import com.netizen.cms.api.academic.model.request.SyllabusInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.SyllabusInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.SyllabusInfoViewResponse;
import com.netizen.cms.api.academic.repository.SyllabusInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;

@Service
public class SyllabusInfoService {

	@Autowired
	private SyllabusInfoRepository syllabusInfoRepository;
	@Autowired
	private FileUtilService fileUtilService;

	public BaseResponse saveSyllabusInfo(SyllabusInfoSaveRequest syllabusInfoSaveRequest) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");


		SyllabusInfo syllabusInfo = new SyllabusInfo();

		syllabusInfo.setCmsInfo(CmsInfo.builder().cmsId(syllabusInfoSaveRequest.getCmsId()).build());
		syllabusInfo.setClassInfo(ClassInfo.builder().classId(syllabusInfoSaveRequest.getClassId()).build());
		syllabusInfo.setGroupInfo(GroupInfo.builder().groupId(syllabusInfoSaveRequest.getGroupId()).build());
		syllabusInfo.setSyllabusSerial(syllabusInfoSaveRequest.getSyllabusSerial());

		if (syllabusInfoSaveRequest.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(syllabusInfoSaveRequest.getFileName()).get();
			String fileName = syllabusInfoSaveRequest.getCmsId() + "_" + syllabusInfoSaveRequest.getClassId()+ "_"
					+ syllabusInfoSaveRequest.getGroupId() + "_syllabus." + fileExtension;
			fileUtilService.writeFileToPath(Folder.SYLLABUS.name(), syllabusInfoSaveRequest.getFileContent(), fileName);
			syllabusInfo.setSyllabusFileName(fileName);
		}

		syllabusInfoRepository.save(syllabusInfo);

		baseResponse.setMessage("Syllabus information successfully saved");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	public BaseResponse updateSyllabusFile(Long syllabusId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();

		Optional<SyllabusInfo> syllabusInfoOpt = syllabusInfoRepository.findById(syllabusId);
		SyllabusInfo syllabusInfo = syllabusInfoOpt.get();

		if (!syllabusInfoOpt.isPresent()) {
			baseResponse.setMessage("No Syllabus Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		String[] fileExtension = fileUtilService.provideFileExtension(file);

		String fileName = syllabusInfo.getCmsInfo().getCmsId() + "_" + syllabusInfo.getSyllabusId() + "_"
				+ syllabusInfo.getClassInfo().getClassId() + syllabusInfo.getGroupInfo().getGroupId() + "_syllabus."
				+ fileExtension[1];
		fileUtilService.fileUpload(Folder.SYLLABUS.name(), fileName, file);
		syllabusInfo.setSyllabusFileName(fileName);
		syllabusInfoRepository.save(syllabusInfo);

		baseResponse.setMessage("Syllabus File Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	public ItemResponse<List<SyllabusInfoViewResponse>> findSyllabusInfos(Long cmsId) {

		List<SyllabusInfo> syllabuss = syllabusInfoRepository.findByCmsInfo_CmsIdOrderBySyllabusSerial(cmsId);

		List<SyllabusInfoViewResponse> syllabusInfoResponses = syllabuss.stream()
				.map(syllabus -> copySyllabusInfoToSyllabusInfoViewResponse(syllabus)).collect(Collectors.toList());

		ItemResponse<List<SyllabusInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(syllabusInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}

	
	public BaseResponse updateSyllabusInfo(SyllabusInfoUpdateRequest syllabusInfoUpdateRequest) {


		BaseResponse baseResponse = new BaseResponse();
		
		SyllabusInfo syllabusInfo = syllabusInfoRepository.findById(syllabusInfoUpdateRequest.getSyllabusId()).get();
		
		if (syllabusInfo == null) {
			baseResponse.setMessage("No syllabus info found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		syllabusInfo.setSyllabusSerial(syllabusInfoUpdateRequest.getSyllabusSerial());

		if (syllabusInfoUpdateRequest.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(syllabusInfoUpdateRequest.getFileName()).get();
			String fileName = syllabusInfo.getCmsInfo().getCmsId() + "_" + syllabusInfo.getClassInfo().getClassId()+ "_"
					+ syllabusInfo.getGroupInfo().getGroupId() + "_syllabus." + fileExtension;
			fileUtilService.writeFileToPath(Folder.SYLLABUS.name(), syllabusInfoUpdateRequest.getFileContent(), fileName);
			syllabusInfo.setSyllabusFileName(fileName);
		}
		syllabusInfo.setClassInfo(ClassInfo.builder().classId(syllabusInfoUpdateRequest.getClassId()).build());
		syllabusInfo.setGroupInfo(GroupInfo.builder().groupId(syllabusInfoUpdateRequest.getGroupId()).build());
		
		syllabusInfoRepository.save(syllabusInfo);
		
		baseResponse.setMessage("Syllabus information successfully update");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	public BaseResponse deleteSyllabusInfo(Long syllabusId) {

		Optional<SyllabusInfo> syllabusInfoOpt = syllabusInfoRepository.findById(syllabusId);

		SyllabusInfo syllabusInfo = null;

		if (syllabusInfoOpt.isPresent()) {
			syllabusInfo = syllabusInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (syllabusInfo == null) {
			baseResponse.setMessage("No Syllabus found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		syllabusInfoRepository.delete(syllabusInfo);
		fileUtilService.deleteFile(Folder.SYLLABUS.name(), syllabusInfo.getSyllabusFileName());

		BaseResponse response = new BaseResponse();
		response.setMessage("Syllabus Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}

	public SyllabusInfoViewResponse copySyllabusInfoToSyllabusInfoViewResponse(SyllabusInfo syllabusInfo) {

		SyllabusInfoViewResponse syllabusInfoViewResponse = SyllabusInfoViewResponse.builder().build();
		BeanUtils.copyProperties(syllabusInfo, syllabusInfoViewResponse);
		syllabusInfoViewResponse.setClassName(syllabusInfo.getClassInfo().getClassName());
		syllabusInfoViewResponse.setClassId(syllabusInfo.getClassInfo().getClassId());
		syllabusInfoViewResponse.setGroupName(syllabusInfo.getGroupInfo().getGroupName());
		syllabusInfoViewResponse.setGroupId(syllabusInfo.getGroupInfo().getGroupId());	
				
		if (!StringUtils.isEmpty(syllabusInfo.getSyllabusFileName())) {
			try {
				syllabusInfoViewResponse.setFileContent(fileUtilService.fetchFileInByte(Folder.SYLLABUS.name(), syllabusInfo.getSyllabusFileName()));
				syllabusInfoViewResponse.setSyllabusFileName(syllabusInfo.getSyllabusFileName()); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}return syllabusInfoViewResponse;

	}

	public Map<String, Object> findSyllabusFile(Long syllabusId) {

		Optional<SyllabusInfo> syllabusInfoOpt = syllabusInfoRepository.findById(syllabusId);

		Map<String, Object> map = new HashMap<>();
		map.put("fileFound", false);

		SyllabusInfo syllabusInfo = null;

		if (syllabusInfoOpt.isPresent()) {
			syllabusInfo = syllabusInfoOpt.get();
		}

		try {

			if (!StringUtils.isEmpty(syllabusInfo.getSyllabusFileName())) {
				map.put("fileFound", true);
				map.put("file",
						fileUtilService.fetchFileInByte(Folder.SYLLABUS.name(), syllabusInfo.getSyllabusFileName()));
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
