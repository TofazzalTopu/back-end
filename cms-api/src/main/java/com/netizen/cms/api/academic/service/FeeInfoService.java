package com.netizen.cms.api.academic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.entity.FeeInfo;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.academic.model.request.FeeInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.FeeInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.FeeInfoViewResponse;
import com.netizen.cms.api.academic.repository.FeeInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;

@Service
public class FeeInfoService {
	
	@Autowired
	private FeeInfoRepository feeInfoRepository;
	
	public BaseResponse saveFeeInfo(FeeInfoSaveRequest feeInfoSaveRequest){
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		
		FeeInfo feeInfo = FeeInfo.builder().build();
		BeanUtils.copyProperties(feeInfoSaveRequest, feeInfo, ApplicationUtils.getNullPropertyNames(feeInfoSaveRequest));
		feeInfo.setCmsInfo(CmsInfo.builder().cmsId(feeInfoSaveRequest.getCmsId()).build());
		feeInfo.setClassInfo(ClassInfo.builder().classId(feeInfoSaveRequest.getClassId()).build()); 
		feeInfo.setGroupInfo(GroupInfo.builder().groupId(feeInfoSaveRequest.getGroupId()).build()); 
		feeInfo.setFeeCreateDate(new Date());
		feeInfoRepository.save(feeInfo);
	
		baseResponse.setMessage("Fee information successfully saved");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	public ItemResponse<List<FeeInfoViewResponse>> findFeeInfos(Long cmsId) {

		List<FeeInfo> fees = feeInfoRepository.findByCmsInfo_CmsIdOrderByFeeSerial(cmsId);
		
		List<FeeInfoViewResponse> feeInfoResponses = fees.stream()
				.map(fee -> copyFeeInfoToFeeInfoViewResponse(fee)).collect(Collectors.toList());

		ItemResponse<List<FeeInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(feeInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
	
	public BaseResponse updateFeeInfo(FeeInfoUpdateRequest feeInfoUpdateRequest){
		
		Optional<FeeInfo> feeInfoOpt = feeInfoRepository.findById(feeInfoUpdateRequest.getFeeId());
		
		
		if(! feeInfoOpt.isPresent()) {
			BaseResponse baseResponse = new BaseResponse();

				baseResponse.setMessage("No Fee found to update.");
				baseResponse.setMessageType(0);
				return baseResponse;
			
		}
		
		FeeInfo feeInfo = feeInfoOpt.get();
		
		BeanUtils.copyProperties(feeInfoUpdateRequest, feeInfo, ApplicationUtils.getNullPropertyNames(feeInfoUpdateRequest));
		feeInfo.setCmsInfo(CmsInfo.builder().cmsId(feeInfoUpdateRequest.getCmsId()).build());
		feeInfo.setClassInfo(ClassInfo.builder().classId(feeInfoUpdateRequest.getClassId()).build()); 
		feeInfo.setGroupInfo(GroupInfo.builder().groupId(feeInfoUpdateRequest.getGroupId()).build()); 
		feeInfo.setFeeCreateDate(new Date());
		feeInfoRepository.save(feeInfo);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("Fee information successfully update");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	public BaseResponse deleteFeeInfo(Long feeId) {

		Optional<FeeInfo> feeInfoOpt = feeInfoRepository.findById(feeId);
		
		FeeInfo feeInfo = null;
		
		if(feeInfoOpt.isPresent()) {
			feeInfo = feeInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (feeInfo == null) {
			baseResponse.setMessage("No Fee found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}


		feeInfoRepository.delete(feeInfo);

		BaseResponse response = new BaseResponse();
		response.setMessage("Fee Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}

	
	public FeeInfoViewResponse copyFeeInfoToFeeInfoViewResponse(FeeInfo feeInfo) {
		
		FeeInfoViewResponse feeInfoViewResponse = FeeInfoViewResponse.builder().build();
		BeanUtils.copyProperties(feeInfo, feeInfoViewResponse);
		feeInfoViewResponse.setClassName(feeInfo.getClassInfo().getClassName());
		feeInfoViewResponse.setClassId(feeInfo.getClassInfo().getClassId());
		feeInfoViewResponse.setGroupName(feeInfo.getGroupInfo().getGroupName());
		feeInfoViewResponse.setGroupId(feeInfo.getGroupInfo().getGroupId());
		return feeInfoViewResponse;		
		
	}
	
	/**
	 * @author riaz_netizen
	 * @since 16-07-2020
	 * @param classId
	 * @param cmsId
	 * @return
	 */
	public ItemResponse<List<FeeInfoViewResponse>> findClassWiseFeesInfo(Long classId,Long cmsId) {

		List<FeeInfo> fees = feeInfoRepository.findByClassInfo_ClassIdAndCmsInfo_CmsId(classId, cmsId);
		
		List<FeeInfoViewResponse> feeInfoResponses = fees.stream()
				.map(fee -> copyFeeInfoToFeeInfoViewResponse(fee)).collect(Collectors.toList());

		ItemResponse<List<FeeInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(feeInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
}
