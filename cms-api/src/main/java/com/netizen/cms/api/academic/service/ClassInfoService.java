package com.netizen.cms.api.academic.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.request.ClassInfoCreateRequest;
import com.netizen.cms.api.academic.model.request.ClassInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.ClassInfoViewResponse;
import com.netizen.cms.api.academic.repository.ClassInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;


@Service
public class ClassInfoService {
	
	@Autowired
	public ClassInfoRepository classInfoRepository;
	
	@Transactional
	public BaseResponse saveClassInfo(ClassInfoCreateRequest request) {
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		
		CmsInfo cmsInfo= CmsInfo.builder().cmsId(request.getCmsId()).build();
		
		ClassInfo checkClassInfo=classInfoRepository.findByClassNameAndCmsInfo(request.getClassName(), cmsInfo);
		
		if(checkClassInfo!=null) {
			baseResponse.setMessage(request.getClassName()+" already exists.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		ClassInfo classInfo;
		
		classInfo=ClassInfo.builder()
		.className(request.getClassName())
		.classSerial(request.getClassSerial())
		.cmsInfo(cmsInfo)
		.build();
		
		classInfoRepository.save(classInfo);
		
		baseResponse.setMessage("Class Info Successfully Created.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	
	
	@Transactional
	public BaseResponse updateClassInfo(ClassInfoUpdateRequest updateRequest) {
		BaseResponse baseResponse=new BaseResponse();
		CmsInfo cmsInfo= CmsInfo.builder().cmsId(updateRequest.getCmsId()).build();
		
		ClassInfo classInfo=classInfoRepository.findByClassIdAndCmsInfo(updateRequest.getClassId(), cmsInfo);
		
		if(classInfo==null) {
			baseResponse.setMessage("No Class Info Found.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		

		classInfo.setClassName(updateRequest.getClassName());
		classInfo.setClassSerial(updateRequest.getClassSerial());
		
		baseResponse.setMessage("Class Info Successfully Updated.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	
	
	
	
	@Transactional
	public BaseResponse deleteClassInfo(Long classId) {
		BaseResponse baseResponse=new BaseResponse();
		
		ClassInfo classInfo=classInfoRepository.findById(classId).get();
		
		if(classInfo==null) {
			baseResponse.setMessage("No Class Found To Delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		
		classInfoRepository.delete(classInfo);
		
		baseResponse.setMessage("Class Info Successfully Deleted.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 19-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemResponse classInfoList(Long cmsId) {
		ItemResponse itemResponse=new ItemResponse();		
		
		List<ClassInfo> classInfos=classInfoRepository.findByCmsInfoOrderByClassSerialAsc(CmsInfo.builder().cmsId(cmsId).build());
		
		List<ClassInfoViewResponse> responses=new ArrayList<>();
		
		for(ClassInfo info:classInfos) {
			
			ClassInfoViewResponse response=new ClassInfoViewResponse();
			response.setClassId(info.getClassId());
			response.setClassName(info.getClassName());
			response.setClassSerial(info.getClassSerial());
			
			responses.add(response);
			
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		return itemResponse;
	}
	
	
	

}
