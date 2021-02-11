package com.netizen.cms.api.menuconfig.service;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.menuconfig.model.entity.CmsMenuConfig;
import com.netizen.cms.api.menuconfig.model.entity.CmsMenuItem;
import com.netizen.cms.api.menuconfig.repository.CmsMenuConfigRepository;
import com.netizen.cms.api.menuconfig.repository.CmsMenuItemRepository;
import com.netizen.cms.api.menuconfig.request.CmsMenuConfigSaveRequest;
import com.netizen.cms.api.menuconfig.response.CmsMenuConfigViewResponse;
import com.netizen.cms.api.menuconfig.response.CmsMenuItemViewResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CmsMenuConfigService {

    @Autowired
    CmsMenuConfigRepository cmsMenuConfigRepository;

    @Autowired
    CmsMenuItemRepository cmsMenuItemRepository;

    @Autowired
    ModelMapper modelMapper;

    public BaseResponse saveCmsMenuConfig(CmsMenuConfigSaveRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("CMS Menu Config Not saved!");
        baseResponse.setMessageType(0);

        CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsInfoId()).build();
        List<CmsMenuConfig> cmsMenuConfigList = cmsMenuConfigRepository.findAllByCmsInfo(cmsInfo);

        if(!cmsMenuConfigList.isEmpty()) {
            cmsMenuConfigRepository.deleteAll(cmsMenuConfigList);
        }

        request.getCmsMenuItemIds().forEach(menuId -> {
            CmsMenuConfig cmsMenuConfig = new CmsMenuConfig();
            cmsMenuConfig.setCmsInfo(cmsInfo);
            cmsMenuConfig.setAssign_user_id(request.getAssign_user_id());
            cmsMenuConfig.setAssignDate(new Date());
            cmsMenuConfig.setCmsMenuItem(CmsMenuItem.builder().menuItemId(menuId).build());
            cmsMenuConfigRepository.save(cmsMenuConfig);
        });

        baseResponse.setMessage("CMS Menu Config saved Successfully!");
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
    public BaseResponse deleteCmsMenuConfig(Long menuItemId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Something went wrong, CMS Menu Config Not Deleted!");
        baseResponse.setMessageType(0);
        CmsMenuConfig cmsMenuConfig = cmsMenuConfigRepository.getOne(menuItemId);
        if (cmsMenuConfig == null) {
            baseResponse.setMessage("CMS Menu Config Id Not Found");
            baseResponse.setMessageType(0);
            return baseResponse;
        }

        cmsMenuConfigRepository.delete(cmsMenuConfig);
        baseResponse.setMessage("CMS Menu Config Deleted Successfully!");
        baseResponse.setMessageType(0);

        return baseResponse;
    }


    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */

    public ItemResponse getAllCmsMenuConfig() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setMessage("Something went wrong!");
        itemResponse.setMessageType(0);
        List<CmsMenuConfig> cmsMenuConfigList = cmsMenuConfigRepository.findAll();

        List<CmsMenuConfigViewResponse> responses = new ArrayList<>();
        List<CmsMenuConfigViewResponse> responseList = mapCmsMenuConfigViewResponse(cmsMenuConfigList);

        itemResponse.setItem(responseList);
        itemResponse.setMessage("OK");
        itemResponse.setMessageType(1);

        return itemResponse;
    }

    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    public List<CmsMenuConfigViewResponse> mapCmsMenuConfigViewResponse(List<CmsMenuConfig> cmsMenuConfigList) {
        List<CmsMenuConfigViewResponse> responses = new ArrayList<>();
        for (CmsMenuConfig conf : cmsMenuConfigList) {
            CmsMenuConfigViewResponse response = new CmsMenuConfigViewResponse();
            response.setMenuConfigId(conf.getMenuConfigId());
            response.setCmsMenuItemId(conf.getCmsMenuItem().getMenuItemId());
            response.setCmsInfoId(conf.getCmsInfo().getCmsId());
            response.setAssign_user_id(conf.getAssign_user_id());
            responses.add(response);
        }
        return responses;
    }

    /**
     * @param cmsInfoId
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    public ItemResponse findAllMenuItemByCmsInfo(Long cmsInfoId) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setMessage("Something went wrong!");
        itemResponse.setMessageType(0);

        CmsInfo cmsInfo = CmsInfo.builder().cmsId(cmsInfoId).build();
        List<CmsMenuItem> cmsMenuItemList = cmsMenuItemRepository.findAllByEnableStatus(1);
        List<CmsMenuConfig> cmsMenuConfigList = cmsMenuConfigRepository.findAllByCmsInfo(cmsInfo);
        List<CmsMenuItemViewResponse> cmsMenuItemViewResponseList = new ArrayList<>();

        cmsMenuItemList.forEach(item -> {
            CmsMenuItemViewResponse viewResponse = modelMapper.map(item, CmsMenuItemViewResponse.class);
            boolean isExists = cmsMenuConfigList.stream().anyMatch(c -> item.equals(c.getCmsMenuItem()));
            if (isExists) {
                viewResponse.setEnableStatus(1);
            } else {
                viewResponse.setEnableStatus(0);
            }
            cmsMenuItemViewResponseList.add(viewResponse);
        });

        itemResponse.setItem(cmsMenuItemViewResponseList);
        itemResponse.setMessage("OK");
        itemResponse.setMessageType(1);
        return itemResponse;
    }
}
