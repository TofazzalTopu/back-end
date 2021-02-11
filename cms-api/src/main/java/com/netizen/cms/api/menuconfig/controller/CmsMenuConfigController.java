package com.netizen.cms.api.menuconfig.controller;

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.menuconfig.request.CmsMenuConfigSaveRequest;
import com.netizen.cms.api.menuconfig.service.CmsMenuConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/menu/config")
public class CmsMenuConfigController {
    @Autowired
    public CmsMenuConfigService menuConfigService;

    /**
     * @param requestSave
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @PostMapping(value = "/save")
    public ResponseEntity<BaseResponse> saveCmsMenuConfig(@RequestBody @Valid CmsMenuConfigSaveRequest requestSave) {
        BaseResponse baseResponse = menuConfigService.saveCmsMenuConfig(requestSave);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    /**
     * @param bookId
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<BaseResponse> deleteCmsMenuConfig(@RequestParam Long bookId) {
        BaseResponse baseResponse = menuConfigService.deleteCmsMenuConfig(bookId);
        return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
    }

    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @GetMapping(value = "/list")
    public ResponseEntity<ItemResponse> getAllCmsMenuConfig() {
        ItemResponse itemResponse = menuConfigService.getAllCmsMenuConfig();
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    /**
     * @param
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @GetMapping(value = "/listForConfigByCmsInfo")
    public ResponseEntity<ItemResponse> findAllMenuItemByCmsInfo(@RequestParam Long cmsInfoId) {
        ItemResponse itemResponse = menuConfigService.findAllMenuItemByCmsInfo(cmsInfoId);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }


}
