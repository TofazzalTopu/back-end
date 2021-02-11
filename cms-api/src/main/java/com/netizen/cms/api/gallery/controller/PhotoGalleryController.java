package com.netizen.cms.api.gallery.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.gallery.model.request.PhotoGallerySaveRequest;
import com.netizen.cms.api.gallery.model.request.PhotoGalleryUpdateRequest;
import com.netizen.cms.api.gallery.service.PhotoGalleryService;

@RestController
@RequestMapping(value = "/gallery")
public class PhotoGalleryController {
	@Autowired
	public PhotoGalleryService photoGalleryService;

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveGalleryContent(@RequestBody @Valid PhotoGallerySaveRequest request) {
		BaseResponse baseResponse = photoGalleryService.savePhotoGallery(request);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param updateRequest
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateGalleryContent(
			@RequestBody @Valid PhotoGalleryUpdateRequest updateRequest) {
		BaseResponse baseResponse = photoGalleryService.updatePhotoGallery(updateRequest);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 06-07-2020
	 * @param photoGalleryId
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/photo/update")
	public ResponseEntity<BaseResponse> updateSngleGalleryPhoto(@RequestParam Long photoGalleryId,
			@RequestParam MultipartFile file) {
		BaseResponse baseResponse = photoGalleryService.updateSinglePhotoGallery(photoGalleryId, file);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param linkId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteGalleryContent(@RequestParam Long photoGalleryId) {
		BaseResponse baseResponse = photoGalleryService.deletePhotoGallery(photoGalleryId);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> GalleryContentList(@RequestParam Long cmsId) {
		ItemResponse itemResponse = photoGalleryService.findGalleryContentList(cmsId);
		return new ResponseEntity<>(itemResponse, HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
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
	 * @since 22-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/count/photo")
	public ResponseEntity<ItemResponse> findTypeWiseGalleryPhotoQuantity(@RequestParam Long cmsId){
		ItemResponse itemResponse=photoGalleryService.findGalleryPhotoQuantity(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
