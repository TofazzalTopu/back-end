package com.netizen.cms.api.menuconfig.service;

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.menuconfig.model.entity.CmsMenuItem;
import com.netizen.cms.api.menuconfig.repository.CmsMenuItemRepository;
import com.netizen.cms.api.menuconfig.request.CmsMenuItemSaveRequest;
import com.netizen.cms.api.menuconfig.request.CmsMenuItemUpdateRequest;
import com.netizen.cms.api.menuconfig.response.CmsMenuItemViewResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CmsMenuItemService {

    @Autowired
    CmsMenuItemRepository cmsMenuItemRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * @param request
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    @Transactional
    public BaseResponse saveCmsMenuItem(CmsMenuItemSaveRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Something went wrong!");
        baseResponse.setMessageType(1);

        CmsMenuItem parentCmsMenuItem = new CmsMenuItem();

        CmsMenuItem cmsMenuItem = new CmsMenuItem();
        cmsMenuItem.setName(request.getName());
        cmsMenuItem.setDefaultCode(request.getDefaultCode());
        cmsMenuItem.setEnableStatus(request.getEnableStatus());

        if (request.getParentStatus() == 0) {
            parentCmsMenuItem = CmsMenuItem.builder().menuItemId(request.getParentCmsMenuItemId()).build();
            cmsMenuItem.setParentCmsMenuItem(parentCmsMenuItem);
        }
        cmsMenuItem.setParentStatus(request.getParentStatus());
        cmsMenuItem.setMenuLayer(request.getMenuLayer());
        cmsMenuItem.setNote(request.getNote());
        cmsMenuItemRepository.save(cmsMenuItem);

        baseResponse.setMessage("CMS Menu Item Saved Successfully!");
        baseResponse.setMessageType(1);

        return baseResponse;
    }

    /**
     * @param request
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    @Transactional
    public BaseResponse updateCmsMenuItem(CmsMenuItemUpdateRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Something went wrong!");
        baseResponse.setMessageType(0);

        CmsMenuItem parentCmsMenuItem = new CmsMenuItem();
        CmsMenuItem cmsMenuItem = cmsMenuItemRepository.findById(request.getMenuItemId()).get();

        if (cmsMenuItem == null) {
            baseResponse.setMessage("CMS Menu Item Not Found To Update.");
            baseResponse.setMessageType(0);
            return baseResponse;
        }
        if (request.getParentCmsMenuItemId() != null) {
            parentCmsMenuItem = CmsMenuItem.builder().menuItemId(request.getParentCmsMenuItemId()).build();
            cmsMenuItem.setParentCmsMenuItem(parentCmsMenuItem);
        }

        cmsMenuItem.setName(request.getName());
        cmsMenuItem.setDefaultCode(request.getDefaultCode());
        cmsMenuItem.setEnableStatus(request.getEnableStatus());
        cmsMenuItem.setParentStatus(request.getParentStatus());
        cmsMenuItem.setMenuLayer(request.getMenuLayer());
        cmsMenuItem.setNote(request.getNote());
        cmsMenuItemRepository.save(cmsMenuItem);

        baseResponse.setMessage("CMS Menu Item Updated Successfully!");
        baseResponse.setMessageType(1);
        return baseResponse;
    }

    /**
     * @param menuItemId
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    @Transactional
    public BaseResponse deleteCmsMenuItem(Long menuItemId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Something went wrong, CMS Menu Item Not Deleted!");
        baseResponse.setMessageType(0);

        CmsMenuItem cmsMenuItem = cmsMenuItemRepository.findById(menuItemId).get();
        if (cmsMenuItem == null) {
            baseResponse.setMessage("CMS Menu Item Not Found!");
            baseResponse.setMessageType(0);
            return baseResponse;
        }
        cmsMenuItemRepository.delete(cmsMenuItem);
        baseResponse.setMessage("CMS Menu Item Deleted Successfully!");
        baseResponse.setMessageType(0);
        return baseResponse;
    }

    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    public ItemResponse getAllCmsMenuItem() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setMessage("Something went wrong!");
        itemResponse.setMessageType(0);

        List<CmsMenuItem> cmsMenuItemList = cmsMenuItemRepository.findAll();
        List<CmsMenuItemViewResponse> responses = mapCmsMenuItemViewResponse(cmsMenuItemList);
        itemResponse.setItem(responses);
        itemResponse.setMessage("OK");
        itemResponse.setMessageType(1);

        return itemResponse;
    }


    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    public ItemResponse findCmsMenuItemByParent(Long menuItemId) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setMessage("Something went wrong!");
        itemResponse.setMessageType(0);

        CmsMenuItem cmsMenuItem = cmsMenuItemRepository.getOne(menuItemId);
        if (cmsMenuItem == null) {
            itemResponse.setMessage("CMS Menu Item Not Found!");
            itemResponse.setMessageType(0);
            return itemResponse;
        }

        List<CmsMenuItem> cmsMenuItemList = cmsMenuItemRepository.findAllByParentCmsMenuItemOrderByNameAsc(cmsMenuItem);
        List<CmsMenuItemViewResponse> responses = mapCmsMenuItemViewResponse(cmsMenuItemList);

        itemResponse.setItem(responses);
        itemResponse.setMessage("OK");
        itemResponse.setMessageType(1);

        return itemResponse;
    }

    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    public List<CmsMenuItemViewResponse> mapCmsMenuItemViewResponse(List<CmsMenuItem> cmsMenuItemList) {
        List<CmsMenuItemViewResponse> responses = new ArrayList<>();
        for (CmsMenuItem conf : cmsMenuItemList) {
            CmsMenuItemViewResponse response = new CmsMenuItemViewResponse();
            response = modelMapper.map(conf, CmsMenuItemViewResponse.class);
            response.setParentCmsMenuItemId(conf.getParentCmsMenuItem() == null ? null : conf.getParentCmsMenuItem().getMenuItemId());
            responses.add(response);
        }
        return responses;
    }
}
