package com.netizen.cms.api.academic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netizen.cms.api.academic.model.entity.GroupInfo;
import com.netizen.cms.api.academic.model.request.GroupInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.GroupInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.GroupInfoViewResponse;
import com.netizen.cms.api.academic.repository.GroupInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.ApplicationUtils;


@Service
public class GroupInfoService {

	@Autowired
	private GroupInfoRepository groupInfoRepository;

	public BaseResponse saveGroupInfo(GroupInfoSaveRequest groupInfoSaveRequest) {
		
		BaseResponse response = new BaseResponse();
		response.setMessageType(0);
		response.setMessage("Something went wrong");
		
		GroupInfo alreadyExistGroupInfo = groupInfoRepository.findByCmsInfo_CmsIdAndGroupName(groupInfoSaveRequest.getCmsId(), groupInfoSaveRequest.getGroupName());
		
		if(alreadyExistGroupInfo != null) {
			
			response.setMessage(groupInfoSaveRequest.getGroupName() + " already exist");
			response.setMessageType(0);
			return response;
		}
		

		GroupInfo groupInfo = GroupInfo.builder().build();
		groupInfo.setGroupName(groupInfoSaveRequest.getGroupName());
		groupInfo.setGroupSerial(groupInfoSaveRequest.getGroupSerial());
		groupInfo.setCmsInfo(CmsInfo.builder().cmsId(groupInfoSaveRequest.getCmsId()).build());

		groupInfoRepository.save(groupInfo);

		
		response.setMessage("Group Info Successfully Saved");
		response.setMessageType(1);
		return response;

	}

	public ItemResponse<List<GroupInfoViewResponse>> findGroupInfos(Long cmsId) {

		List<GroupInfo> groups = groupInfoRepository.findByCmsInfo_CmsIdOrderByGroupSerial(cmsId);
		
		List<GroupInfoViewResponse> groupInfoResponses = groups.stream()
				.map(group -> copyGroupInfoToGroupInfoViewResponse(group)).collect(Collectors.toList());

		ItemResponse<List<GroupInfoViewResponse>> response = new ItemResponse<>();
		response.setItem(groupInfoResponses);
		response.setMessageType(1);
		response.setMessage("OK");

		return response;

	}

	public BaseResponse updateGroupInfo(GroupInfoUpdateRequest groupInfoUpdateRequest) {

		Optional<GroupInfo> groupInfoOpt = groupInfoRepository.findById(groupInfoUpdateRequest.getGroupId());
		GroupInfo groupInfo = null;
		
		if(groupInfoOpt.isPresent()) {
			groupInfo = groupInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (groupInfo == null) {
			baseResponse.setMessage("No Group found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		BeanUtils.copyProperties(groupInfoUpdateRequest, groupInfo,
				ApplicationUtils.getNullPropertyNames(groupInfoUpdateRequest));

		groupInfoRepository.save(groupInfo);

		BaseResponse response = new BaseResponse();
		response.setMessage("Group Info Successfully Updated");
		response.setMessageType(1);

		return response;
	}
	
	public BaseResponse deleteGroupInfo(Long groupId) {

		Optional<GroupInfo> groupInfoOpt = groupInfoRepository.findById(groupId);
		
		GroupInfo groupInfo = null;
		
		if(groupInfoOpt.isPresent()) {
			groupInfo = groupInfoOpt.get();
		}

		BaseResponse baseResponse = new BaseResponse();

		if (groupInfo == null) {
			baseResponse.setMessage("No Group found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}


		groupInfoRepository.delete(groupInfo);

		BaseResponse response = new BaseResponse();
		response.setMessage("Group Info Successfully Deleted");
		response.setMessageType(1);

		return response;
	}

	public GroupInfoViewResponse copyGroupInfoToGroupInfoViewResponse(GroupInfo groupInfo) {

		GroupInfoViewResponse groupInfoViewResponse = GroupInfoViewResponse.builder().build();
		BeanUtils.copyProperties(groupInfo, groupInfoViewResponse);
		return groupInfoViewResponse;
	}

}
