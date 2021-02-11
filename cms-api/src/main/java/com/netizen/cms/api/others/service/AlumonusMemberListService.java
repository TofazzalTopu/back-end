package com.netizen.cms.api.others.service;

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
import com.netizen.cms.api.others.model.entity.AlumonusMemberList;
import com.netizen.cms.api.others.model.request.AlumonusMemberListSaveRequest;
import com.netizen.cms.api.others.model.request.AlumonusMemberListUpdateRequest;
import com.netizen.cms.api.others.model.response.AlumonusMemberListViewResponse;
import com.netizen.cms.api.others.repository.AlumonusMemberListRepository;

@Service
public class AlumonusMemberListService {

	@Autowired
	public AlumonusMemberListRepository alumonusMemberListRepository;
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveAlumonusMembers(AlumonusMemberListSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		AlumonusMemberList alumonusMemberList = new AlumonusMemberList();
		alumonusMemberList.setCmsInfo(cmsInfo);
		alumonusMemberList.setSerial(request.getSerial());
		alumonusMemberList.setName(request.getName());
		alumonusMemberList.setContactNo(request.getContactNo());
		alumonusMemberList.setDesignation(request.getDesignation());
		alumonusMemberList.setBatch(request.getBatch());
		alumonusMemberList.setEmail(request.getEmail());
		alumonusMemberList.setOrganization(request.getOrganization());
		alumonusMemberList.setDetails(request.getDetails());
		alumonusMemberList.setCreateBy(request.getCreateBy());
		alumonusMemberList.setCreateDate(new Date());

		AlumonusMemberList saveAlumonusMemberList = alumonusMemberListRepository.save(alumonusMemberList);

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getImageName()).get();
			String fileName = alumonusMemberList.getCmsInfo().getCmsId() + "_"
					+ saveAlumonusMemberList.getAlumonusMemberId() + "_" + "alumonusMember." + fileExtension;
			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
			alumonusMemberList.setImageName(fileName);
		}

		baseResponse.setMessage("Aumonus Member Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateAlumonusMembers(AlumonusMemberListUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		AlumonusMemberList alumonusMemberList = alumonusMemberListRepository.findById(request.getAlumonusMemberId())
				.get();

		if (alumonusMemberList == null) {
			baseResponse.setMessage("No alumonus member found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		alumonusMemberList.setSerial(request.getSerial());
		alumonusMemberList.setName(request.getName());
		alumonusMemberList.setContactNo(request.getContactNo());
		alumonusMemberList.setDesignation(request.getDesignation());
		alumonusMemberList.setBatch(request.getBatch());
		alumonusMemberList.setEmail(request.getEmail());
		alumonusMemberList.setOrganization(request.getOrganization());
		alumonusMemberList.setDetails(request.getDetails());
		alumonusMemberList.setModifiedBy(request.getModifiedBy());
		alumonusMemberList.setModifiedDate(new Date());

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getImageName()).get();
			String fileName = alumonusMemberList.getCmsInfo().getCmsId() + "_" + request.getAlumonusMemberId() + "_"
					+ "alumonusMember." + fileExtension;
			fileUtilService.writeFileToPath(Folder.MEMBER.name(), request.getFileContent(), fileName);
			alumonusMemberList.setImageName(fileName);
		}

		alumonusMemberListRepository.save(alumonusMemberList);

		baseResponse.setMessage("Aumonus Member Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	
	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param alumonusMemberId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteAlumonusMembers(Long alumonusMemberId) {

		BaseResponse baseResponse = new BaseResponse();

		AlumonusMemberList alumonusMemberList = alumonusMemberListRepository.findById(alumonusMemberId).get();

		if (alumonusMemberList == null) {
			baseResponse.setMessage("No Aumonus Member Info found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		alumonusMemberListRepository.delete(alumonusMemberList);
		fileUtilService.deleteFile(Folder.MEMBER.name(), alumonusMemberList.getImageName());

		baseResponse.setMessage("Aumonus Member Info Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 27-09-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchAlumonusMembers(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(cmsId).build();

		List<AlumonusMemberList> alumonusMembers = alumonusMemberListRepository.findByCmsInfoOrderBySerialAsc(cmsInfo);

		List<AlumonusMemberListViewResponse> responses = new ArrayList<>();

		for (AlumonusMemberList info : alumonusMembers) {
			AlumonusMemberListViewResponse response = new AlumonusMemberListViewResponse();
			copyAlumonusMemberInfoToAlumonusMemberInfoViewResponse(info, response);

			if (!StringUtils.isEmpty(info.getImageName())) {
				try {
					response.setImgContent(
							fileUtilService.fetchFileInByte(Folder.MEMBER.name(), info.getImageName()));
					response.setImageName(info.getImageName());
					
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

	public void copyAlumonusMemberInfoToAlumonusMemberInfoViewResponse(AlumonusMemberList alumonusMembers, AlumonusMemberListViewResponse alumonusMemberViewInfo) {
		
		alumonusMemberViewInfo.setAlumonusMemberId(alumonusMembers.getAlumonusMemberId());		
		alumonusMemberViewInfo.setSerial(alumonusMembers.getSerial());
		alumonusMemberViewInfo.setName(alumonusMembers.getName());
		alumonusMemberViewInfo.setDesignation(alumonusMembers.getDesignation());
		alumonusMemberViewInfo.setContactNo(alumonusMembers.getContactNo());
		alumonusMemberViewInfo.setBatch(alumonusMembers.getBatch());
		alumonusMemberViewInfo.setEmail(alumonusMembers.getEmail());
		alumonusMemberViewInfo.setOrganization(alumonusMembers.getOrganization());
		alumonusMemberViewInfo.setDetails(alumonusMembers.getDetails());
		alumonusMemberViewInfo.setCreateBy(alumonusMembers.getCreateBy());
		alumonusMemberViewInfo.setCreateDate(alumonusMembers.getCreateDate());
		alumonusMemberViewInfo.setModifiedBy(alumonusMembers.getModifiedBy());
		alumonusMemberViewInfo.setModifiedDate(alumonusMembers.getModifiedDate());

	}

}
