package com.netizen.cms.api.gallery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.gallery.model.entity.ImportantLinkInfo;
import com.netizen.cms.api.gallery.model.request.ImportantLinkInfoSaveRequest;
import com.netizen.cms.api.gallery.model.request.ImportantLinkInfoUpdateRequest;
import com.netizen.cms.api.gallery.model.response.ImportantLinkInfoViewResponse;
import com.netizen.cms.api.gallery.repository.ImportantLinkInfoRepository;

@Service
public class ImportantLinkInfoService {
	
	@Autowired
	private ImportantLinkInfoRepository importantLinkInfoRepository;

	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveImportantLinkInfo(ImportantLinkInfoSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build(); 	

		ImportantLinkInfo importantLinkInfo = new ImportantLinkInfo();
		importantLinkInfo.setCmsInfo(cmsInfo);
		importantLinkInfo.setCreatedBy(request.getUsername());
		importantLinkInfo.setLinkSerial(request.getLinkSerial());
		importantLinkInfo.setLinkTitle(request.getLinkTitle());
		importantLinkInfo.setLinkUrl(request.getLinkUrl());
		importantLinkInfo.setLinkStatus(1);
		importantLinkInfo.setLinkCreateDate(new Date());

		importantLinkInfoRepository.save(importantLinkInfo);

		baseResponse.setMessage("Important Link Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param request
	 * @return
	 */

	@Transactional
	public BaseResponse updateImportantLinkInfo(ImportantLinkInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

	
		ImportantLinkInfo linkInfo = importantLinkInfoRepository.findById(request.getLinkId()).get();

		if (linkInfo == null) {
			baseResponse.setMessage("No link found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		linkInfo.setLinkSerial(request.getLinkSerial());
		linkInfo.setLinkTitle(request.getLinkTitle());
		linkInfo.setLinkUrl(request.getLinkUrl());
		linkInfo.setModifiedBy(request.getUsername());
		linkInfo.setModifiedDate(new Date());
		

		baseResponse.setMessage("Important Link Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @param linkId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteImportantLinkInfo(Long linkId) {

		BaseResponse baseResponse = new BaseResponse();

		ImportantLinkInfo linkInfo = importantLinkInfoRepository.findById(linkId).get();
		if (linkInfo == null) {
			baseResponse.setMessage("No link found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		importantLinkInfoRepository.delete(linkInfo);

		baseResponse.setMessage("Important Link Info Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 30-06-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchImportantLinkInfoList(Long cmsId) {
		
		ItemResponse itemResponse=new ItemResponse();

		List<ImportantLinkInfo> importantLinkInfos=importantLinkInfoRepository.findByCmsInfo_cmsIdOrderByLinkSerialAsc(cmsId);
		
		List<ImportantLinkInfoViewResponse> responses=new ArrayList<>();
		
		for(ImportantLinkInfo info : importantLinkInfos) {
			ImportantLinkInfoViewResponse response=new ImportantLinkInfoViewResponse();
			copyImportantLinkInfoToImportantLinkInfoViewResponse(info, response);
			responses.add(response);			
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		
		return itemResponse;
	}

	
	
	public void copyImportantLinkInfoToImportantLinkInfoViewResponse(ImportantLinkInfo linkInfo,ImportantLinkInfoViewResponse importantLinkInfoViewResponse) {
		
		importantLinkInfoViewResponse.setLinkId(linkInfo.getLinkId());
		importantLinkInfoViewResponse.setLinkSerial(linkInfo.getLinkSerial());
		importantLinkInfoViewResponse.setLinkTitle(linkInfo.getLinkTitle());
		importantLinkInfoViewResponse.setLinkUrl(linkInfo.getLinkUrl());
		
	}

}
