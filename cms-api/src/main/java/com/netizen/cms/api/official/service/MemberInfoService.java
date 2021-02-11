package com.netizen.cms.api.official.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.official.model.entity.MemberInfo;
import com.netizen.cms.api.official.model.request.MemberInfoSaveRequest;
import com.netizen.cms.api.official.model.request.MemberInfoUpdateRequest;
import com.netizen.cms.api.official.model.response.MemberInfoViewResponse;
import com.netizen.cms.api.official.repository.MemberInfoRepository;

@Service
public class MemberInfoService {

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@Autowired
	private FileUtilService fileUtilService;

	@Transactional
	public BaseResponse saveMemberInfo(MemberInfoSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setCmsInfo(cmsInfo);
		memberInfo.setCreatedBy(request.getUserNmae());
		memberInfo.setFacebookProfile(request.getFacebookProfile());
		memberInfo.setLinkedinProfile(request.getLinkedinProfile());
		memberInfo.setMemberDesignation(request.getMemberDesignation());
		memberInfo.setMemberEmail(request.getMemberEmail());
		memberInfo.setMemberMobile(request.getMemberMobile());
		memberInfo.setMemberName(request.getMemberName());
		memberInfo.setMemberSerial(request.getMemberSerial());
		memberInfo.setMemberStatus(1);
		memberInfo.setTwitterProfile(request.getTwitterProfile());
		memberInfo.setMemberType(request.getMemberType());

		MemberInfo saveMemberInfo = memberInfoRepository.save(memberInfo);

		if (request.isImageSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getImageName()).get();
			String imageName = memberInfo.getCmsInfo().getCmsId() + "_" + saveMemberInfo.getMemberId() + "_"
					+ memberInfo.getMemberMobile() + "_" + "member." + fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.MEMBER.name(), imageName, fileExtension,
					request.getImageContent(), 200, 200);

			memberInfo.setMemberImgName(imageName);
		}

		baseResponse.setMessage("Member Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	@Transactional
	public BaseResponse updateMemberInfo(MemberInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		MemberInfo memberInfo = memberInfoRepository.findById(request.getMemberId()).get();

		if (memberInfo == null) {
			baseResponse.setMessage("No member found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		memberInfo.setFacebookProfile(request.getFacebookProfile());
		memberInfo.setLinkedinProfile(request.getLinkedinProfile());
		memberInfo.setMemberDesignation(request.getMemberDesignation());
		memberInfo.setMemberEmail(request.getMemberEmail());
		memberInfo.setMemberMobile(request.getMemberMobile());
		memberInfo.setMemberName(request.getMemberName());
		memberInfo.setMemberSerial(request.getMemberSerial());
		memberInfo.setMemberStatus(request.getMemberStatus());
		memberInfo.setMemberType(request.getMemberType());
		memberInfo.setTwitterProfile(request.getTwitterProfile());
		memberInfo.setModifiedBy(request.getUserName());
		memberInfo.setModifiedDate(new Date());
		
		if (request.isImageSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getImageName()).get();
			String imageName = memberInfo.getCmsInfo().getCmsId() + "_" + memberInfo.getMemberId() + "_"
					+ memberInfo.getMemberMobile() + "_" + "member." + fileExtension;
			fileUtilService.uploadImgHeightWidth(Folder.MEMBER.name(), imageName, fileExtension,
					request.getImageContent(), 200, 200);

			memberInfo.setMemberImgName(imageName);
		}

		baseResponse.setMessage("Member Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	public BaseResponse updateSingleMemberPhoto(Long memberId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();

		MemberInfo memberInfo = memberInfoRepository.findById(memberId).get();
		if (memberInfo == null) {
			baseResponse.setMessage("No Member Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		String imageName = memberInfo.getCmsInfo().getCmsId() + "_" + memberInfo.getMemberId() + "_"
				+ memberInfo.getMemberMobile() + ".jpg";
		fileUtilService.uploadImgHeightWidth(Folder.MEMBER.name(), imageName, file, 200, 200);
		memberInfo.setMemberImgName(imageName);
		memberInfoRepository.save(memberInfo);

		baseResponse.setMessage("Member Image Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	@Transactional
	public BaseResponse deleteMemberInfo(Long memberId) {

		BaseResponse baseResponse = new BaseResponse();

		MemberInfo memberInfo = memberInfoRepository.findById(memberId).get();

		if (memberInfo == null) {
			baseResponse.setMessage("No member found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		memberInfoRepository.delete(memberInfo);
		fileUtilService.deleteFile(Folder.MEMBER.name(), memberInfo.getMemberImgName());
		
		baseResponse.setMessage("Member Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchMemberInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<MemberInfo> memberInfos = memberInfoRepository
				.findByCmsInfoOrderByMemberSerialAsc(CmsInfo.builder().cmsId(cmsId).build());

		List<MemberInfoViewResponse> responses = new ArrayList<>();

		for (MemberInfo info : memberInfos) {
			MemberInfoViewResponse response = new MemberInfoViewResponse();
			copyMemberInfoToMemberInfoViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchMemberInfoListWithPhoto(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(cmsId).build();

		List<MemberInfo> memberInfos = memberInfoRepository.findByCmsInfoOrderByMemberSerialAsc(cmsInfo);

		List<MemberInfoViewResponse> responses = new ArrayList<>();

		for (MemberInfo info : memberInfos) {
			MemberInfoViewResponse response = new MemberInfoViewResponse();
			copyMemberInfoToMemberInfoViewResponse(info, response);

			if (!StringUtils.isEmpty(info.getMemberImgName())) {
				try {
					response.setMemberImg(
							fileUtilService.fetchFileInByte(Folder.MEMBER.name(), info.getMemberImgName()));
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

	public void copyMemberInfoToMemberInfoViewResponse(MemberInfo memberInfo, MemberInfoViewResponse memberViewInfo) {

		memberViewInfo.setFacebookProfile(memberInfo.getFacebookProfile());
		memberViewInfo.setLinkedinProfile(memberInfo.getLinkedinProfile());
		memberViewInfo.setMemberDesignation(memberInfo.getMemberDesignation());
		memberViewInfo.setMemberEmail(memberInfo.getMemberEmail());
		memberViewInfo.setMemberId(memberInfo.getMemberId());
		memberViewInfo.setMemberImgName(memberInfo.getMemberImgName());
		memberViewInfo.setMemberMobile(memberInfo.getMemberMobile());
		memberViewInfo.setMemberName(memberInfo.getMemberName());
		memberViewInfo.setMemberSerial(memberInfo.getMemberSerial());
		memberViewInfo.setMemberStatus(memberInfo.getMemberStatus());
		memberViewInfo.setMemberType(memberInfo.getMemberType());
		memberViewInfo.setTwitterProfile(memberInfo.getTwitterProfile());

	}

	/**
	 * @author riaz_netizen
	 * @since 08-07-2020
	 * @param cmsId
	 * @param memberType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findTypeWiseMemberListForClient(String memberType, Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<MemberInfo> memberInfos = memberInfoRepository
				.findByMemberTypeAndCmsInfo_CmsIdOrderByMemberSerialAsc(memberType, cmsId);

		List<MemberInfoViewResponse> responses = new ArrayList<>();

		for (MemberInfo info : memberInfos) {
			MemberInfoViewResponse response = new MemberInfoViewResponse();

			response.setMemberId(info.getMemberId());
			response.setMemberName(info.getMemberName());
			response.setMemberDesignation(info.getMemberDesignation());
			response.setMemberMobile(info.getMemberMobile());
			response.setMemberEmail(info.getMemberEmail());
			response.setFacebookProfile(info.getFacebookProfile());
			response.setLinkedinProfile(info.getLinkedinProfile());
			response.setTwitterProfile(info.getTwitterProfile());
			response.setMemberImgName(info.getMemberImgName());

			if (!StringUtils.isEmpty(info.getMemberImgName())) {
				try {
					response.setMemberImg(
							fileUtilService.fetchFileInByte(Folder.MEMBER.name(), info.getMemberImgName()));
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
