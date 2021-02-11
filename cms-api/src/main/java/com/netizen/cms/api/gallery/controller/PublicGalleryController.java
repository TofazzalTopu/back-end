package com.netizen.cms.api.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.gallery.service.DownloadCornerService;
import com.netizen.cms.api.gallery.service.ImportantLinkInfoService;
import com.netizen.cms.api.gallery.service.PhotoGalleryService;

@RestController
@RequestMapping("/public")
public class PublicGalleryController {
	
	@Autowired
	private PhotoGalleryService photoGalleryService;
	@Autowired
	private ImportantLinkInfoService importantLinkInfoService;
	@Autowired
	private DownloadCornerService downloadCornerService;
	
	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/photoGallerys")
	public ResponseEntity<ItemResponse> GalleryContentList(@RequestParam Long cmsId){
		ItemResponse itemResponse=photoGalleryService.findGalleryContentList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param cmsId
	 * @param photoType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/typeWise/photoGallerys")
	public ResponseEntity<ItemResponse> getTypewiseGalleryPhoto(@RequestParam Long cmsId,@RequestParam String photoType){
		ItemResponse itemResponse=photoGalleryService.findTypeWiseGalleryPhoto(cmsId, photoType);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/importantLinks")
	public ResponseEntity<ItemResponse> importantLinkInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=importantLinkInfoService.searchImportantLinkInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/downloadCorners")
	public ResponseEntity<ItemResponse> downloadCornerList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = downloadCornerService.searchDownloadCornerList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}

	
	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param fileId
	 * @return
	 */
	@GetMapping(value = "/downloadcorner/file/find")
	public ResponseEntity<?> findDownloadCornerFile(@RequestParam Long fileId){
		return new ResponseEntity<>(downloadCornerService.findDownloadCornerFile(fileId),HttpStatus.FOUND);
	}
	
	
	
}
