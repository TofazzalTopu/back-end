package com.netizen.cms.api.academic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.academic.model.entity.SeatInfo;
import com.netizen.cms.api.academic.model.request.SeatInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.SeatInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.SeatInfoViewResponse;
import com.netizen.cms.api.academic.repository.SeatInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;


@Service
public class SeatInfoService {
	
	@Autowired
	private SeatInfoRepository seatInfoRepository;
	
	public BaseResponse saveSeatInfo(SeatInfoSaveRequest seatInfoSaveRequest){
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		
		SeatInfo seatInfo = SeatInfo.builder().build();
		BeanUtils.copyProperties(seatInfoSaveRequest, seatInfo, ApplicationUtils.getNullPropertyNames(seatInfoSaveRequest));
		seatInfo.setCmsInfo(CmsInfo.builder().cmsId(seatInfoSaveRequest.getCmsId()).build());
		seatInfo.setClassInfo(ClassInfo.builder().classId(seatInfoSaveRequest.getClassId()).build()); 
		seatInfo.setGroupInfo(GroupInfo.builder().groupId(seatInfoSaveRequest.getGroupId()).build()); 
		seatInfoRepository.save(seatInfo);
		
		baseResponse.setMessage("Seat information successfully saved");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	public ItemResponse<List<SeatInfoViewResponse>> findSeatInfos(Long cmsId) {

		List<SeatInfo> seats = seatInfoRepository.findByCmsInfo_CmsId(cmsId);
		
		List<SeatInfoViewResponse> seatInfoResponses = seats.stream()
				.map(seat -> copySeatInfoToSeatInfoViewResponse(seat)).collect(Collectors.toList());

		ItemResponse<List<SeatInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(seatInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;
	}
	
public BaseResponse updateSeatInfo(SeatInfoUpdateRequest seatInfoUpdateRequest){
	
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		
		Optional<SeatInfo> seatInfoOpt = seatInfoRepository.findById(seatInfoUpdateRequest.getSeatId());
		
		
		if(! seatInfoOpt.isPresent()) {
		

				baseResponse.setMessage("No Seat found to update.");
				baseResponse.setMessageType(0);
				return baseResponse;
			
		}
		
		SeatInfo seatInfo = seatInfoOpt.get();
		
		BeanUtils.copyProperties(seatInfoUpdateRequest, seatInfo, ApplicationUtils.getNullPropertyNames(seatInfoUpdateRequest));
		seatInfo.setCmsInfo(CmsInfo.builder().cmsId(seatInfoUpdateRequest.getCmsId()).build());
		seatInfo.setClassInfo(ClassInfo.builder().classId(seatInfoUpdateRequest.getClassId()).build()); 
		seatInfo.setGroupInfo(GroupInfo.builder().groupId(seatInfoUpdateRequest.getGroupId()).build()); 
		seatInfoRepository.save(seatInfo);
		
		baseResponse.setMessage("Seat information successfully update");
		baseResponse.setMessageType(1); 
		return baseResponse;
	}
	
	public BaseResponse deleteSeatInfo(Long seatId) {

		Optional<SeatInfo> seatInfoOpt = seatInfoRepository.findById(seatId);
		
		SeatInfo seatInfo = null;
		
		if(seatInfoOpt.isPresent()) {
			seatInfo = seatInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (seatInfo == null) {
			baseResponse.setMessage("No Seat found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}


		seatInfoRepository.delete(seatInfo);

		BaseResponse response = new BaseResponse();
		response.setMessage("Seat Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}

	
	public SeatInfoViewResponse copySeatInfoToSeatInfoViewResponse(SeatInfo seatInfo) {
		
		SeatInfoViewResponse seatInfoViewResponse = SeatInfoViewResponse.builder().build();
		BeanUtils.copyProperties(seatInfo, seatInfoViewResponse);
		seatInfoViewResponse.setClassName(seatInfo.getClassInfo().getClassName());
		seatInfoViewResponse.setClassId(seatInfo.getClassInfo().getClassId());
		seatInfoViewResponse.setGroupName(seatInfo.getGroupInfo().getGroupName());
		seatInfoViewResponse.setGroupId(seatInfo.getGroupInfo().getGroupId());
		return seatInfoViewResponse;		
		
	}

}
