package com.netizen.cms.api.cmsinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netizen.cms.api.cmsinfo.model.entity.CmsEdumanMapping;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.cmsinfo.model.request.CmsEdumanMappingSaveRequest;
import com.netizen.cms.api.cmsinfo.model.request.CmsEdumanMappingUpdateRequest;
import com.netizen.cms.api.cmsinfo.model.response.CmsEdumanMappingViewResponse;
import com.netizen.cms.api.cmsinfo.repository.CmsEdumanMappingRepository;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@Service
public class CmsEdumanMappingService {
	@Autowired
	private CmsEdumanMappingRepository cmsEdumanMappingRepository;
	
	/**
	 * @author riaz_netizen
	 * @since 25-08-2020
	 * @param request
	 * @return
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ItemResponse saveCmsEdumanMapping(CmsEdumanMappingSaveRequest request) {

		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setMessageType(0);
		itemResponse.setMessage("Something went wrong");		
		
		CmsInfo cmsInfo= CmsInfo.builder().cmsId(request.getCmsId()).build();
		
		CmsEdumanMapping instituteExistence = cmsEdumanMappingRepository.findByInstituteIdAndCmsInfo_CmsId(request.getInstituteId(), request.getCmsId());
		
		
		if (instituteExistence != null) {
			itemResponse.setMessage("This Institute already Mapped.");
			itemResponse.setMessageType(0);
			return itemResponse;
		}
		
		CmsEdumanMapping cmsEdumanMapping = new CmsEdumanMapping();

		cmsEdumanMapping.setCmsInfo(cmsInfo);
		cmsEdumanMapping.setInstituteId(request.getInstituteId());
		cmsEdumanMapping.setInstituteName(request.getInstituteName());
		cmsEdumanMapping.setInstituteContact(request.getInstituteContact());
		cmsEdumanMapping.setPurchaseCode(request.getPurchaseCode());
		cmsEdumanMapping.setApprovedStatus(0);	
		cmsEdumanMapping.setMappedDate(new Date());

		cmsEdumanMapping=cmsEdumanMappingRepository.save(cmsEdumanMapping);
		
		itemResponse.setMessage("Cms Eduman Mapping Successfully Created.");
		itemResponse.setMessageType(1);
		itemResponse.setItem(cmsEdumanMapping.getCmsMappingId());

		return itemResponse;
	}
	
	/**
	 * @author riaz_netizen
	 * @since 25-08-2020
	 * @param instituteId
	 * @param cmsId
	 * @return
	 */
	public Boolean checkMappingInstituteExistence(String instituteId,Long cmsId) {
		CmsEdumanMapping cmsEdumanMapping = cmsEdumanMappingRepository.findByInstituteIdAndCmsInfo_CmsId(instituteId, cmsId);
		if (cmsEdumanMapping == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @param cmsEdumanMappingUpdateRequest
	 * @return
	 */
	@Transactional
	public BaseResponse approvedCmsMapping(CmsEdumanMappingUpdateRequest cmsEdumanMappingUpdateRequest) {
		BaseResponse baseResponse=new BaseResponse();
		
		CmsEdumanMapping cmsEdumanMapping=cmsEdumanMappingRepository.findById(cmsEdumanMappingUpdateRequest.getCmsMappingId()).get();
		
		if(cmsEdumanMapping==null) {
			baseResponse.setMessage("No mapping info found to approve");
			baseResponse.setMessageType(0);
			return baseResponse;
		}
		

		cmsEdumanMapping.setApprovedStatus(cmsEdumanMappingUpdateRequest.getApprovedStatus());
		cmsEdumanMapping.setApprovedDate(new Date());
		cmsEdumanMappingRepository.save(cmsEdumanMapping);
		
		baseResponse.setMessage("Cms Eduman Mapping Successfully Approved.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}
	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemResponse pendingCmsInstituteMappingList() {
		ItemResponse itemResponse=new ItemResponse();		
		
		List<CmsEdumanMapping> cmsEdumanMappings=cmsEdumanMappingRepository.findByApprovedStatus(0);
		
		List<CmsEdumanMappingViewResponse> responses=new ArrayList<>();
		
		for(CmsEdumanMapping info:cmsEdumanMappings) {
			
			CmsEdumanMappingViewResponse response=new CmsEdumanMappingViewResponse();
			
			response.setCmsId(info.getCmsInfo().getCmsId());
			response.setUrlName(info.getCmsInfo().getUrlName());
			response.setCmsMappingId(info.getCmsMappingId());
			response.setInstituteId(info.getInstituteId());
			response.setInstituteName(info.getInstituteName());
			response.setInstituteContact(info.getInstituteContact());
			response.setApprovedStatus(info.getApprovedStatus());			
			
			responses.add(response);
			
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		return itemResponse;
	}

	
	/**
	 * @author riaz_netizen
	 * @since 24-08-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemResponse approvedMappingInstituteList(Long cmsId) {
		ItemResponse itemResponse=new ItemResponse();	
			
		List<CmsEdumanMapping> cmsEdumanMappings=cmsEdumanMappingRepository.findByCmsInfo_CmsId(cmsId);
		
		List<CmsEdumanMappingViewResponse> responses=new ArrayList<>();
		
		for(CmsEdumanMapping info:cmsEdumanMappings) {
			
			CmsEdumanMappingViewResponse response=new CmsEdumanMappingViewResponse();
			
			response.setCmsId(info.getCmsInfo().getCmsId());
			response.setUrlName(info.getCmsInfo().getUrlName());
			response.setCmsMappingId(info.getCmsMappingId());
			response.setInstituteId(info.getInstituteId());
			response.setInstituteName(info.getInstituteName());
			response.setInstituteContact(info.getInstituteContact());
			response.setApprovedStatus(info.getApprovedStatus());			
			
			responses.add(response);
			
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		return itemResponse;
	}
	
	

}
