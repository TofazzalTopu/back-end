package com.netizen.cms.api.menuconfig.controller;

import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.menuconfig.request.CmsMenuItemSaveRequest;
import com.netizen.cms.api.menuconfig.request.CmsMenuItemUpdateRequest;
import com.netizen.cms.api.menuconfig.service.CmsMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/menu/item")
public class CmsMenuItemController {

    @Autowired
    public CmsMenuItemService cmsMenuItemService;

    /**
     * @param requesSave
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @PostMapping(value = "/save")
    public ResponseEntity<BaseResponse> saveCmsMenuItem(@RequestBody @Valid CmsMenuItemSaveRequest requesSave) {
        BaseResponse baseResponse = cmsMenuItemService.saveCmsMenuItem(requesSave);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    /**
     * @param requestUpdate
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @PutMapping(value = "/update")
    public ResponseEntity<BaseResponse> updateCmsMenuItem(@RequestBody @Valid CmsMenuItemUpdateRequest requestUpdate) {
        BaseResponse baseResponse = cmsMenuItemService.updateCmsMenuItem(requestUpdate);
        return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
    }

    /**
     * @param menuItemId
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<BaseResponse> deleteCmsMenuItem(@RequestParam Long menuItemId) {
        BaseResponse baseResponse = cmsMenuItemService.deleteCmsMenuItem(menuItemId);
        return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
    }


    /**
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @GetMapping(value = "/list")
    public ResponseEntity<ItemResponse> getAllCmsMenuItem() {
        ItemResponse itemResponse = cmsMenuItemService.getAllCmsMenuItem();
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    /**
     * @param menuItemId
     * @return
     * @author tofazzal_netizen
     * @since 01-02-2020
     */
    @GetMapping(value = "/findByParent")
    public ResponseEntity<ItemResponse> findCmsMenuItemByParent(@RequestParam Long menuItemId) {
        ItemResponse itemResponse = cmsMenuItemService.findCmsMenuItemByParent(menuItemId);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }
}


