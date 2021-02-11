package com.netizen.cms.api.gallery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.netizen.cms.api.gallery.model.entity.PhotoGallery;
import com.netizen.cms.api.gallery.model.request.PhotoGallerySaveRequest;
import com.netizen.cms.api.gallery.model.request.PhotoGalleryUpdateRequest;
import com.netizen.cms.api.gallery.model.response.PhotoGalleryViewResponse;
import com.netizen.cms.api.gallery.repository.PhotoGalleryRepository;

@Service
public class PhotoGalleryService {

	@Autowired
	private PhotoGalleryRepository photoGalleryRepository;
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse savePhotoGallery(PhotoGallerySaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		PhotoGallery photoGallery = new PhotoGallery();
		photoGallery.setCmsInfo(cmsInfo);
		photoGallery.setPhotoSerial(request.getPhotoSerial());
		photoGallery.setPhotoType(request.getPhotoType());
		photoGallery.setPhotoTitle(request.getPhotoTitle());
		photoGallery.setPhotoDetails(request.getPhotoDetails());
		photoGallery.setPhotoStatus(1);
		photoGallery.setPhotoCreateDate(new Date());

		PhotoGallery savedPhotoGallery = photoGalleryRepository.save(photoGallery);
				
		
		if (request.isFileSaveOrEditable()) {
			
			String[] photoTypes = photoGallery.getPhotoType().split(" ");
			
			String photoType = "";
			
			for(String pType : photoTypes) {
				
				photoType += pType + "_";			
			}			
			
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String imageName = savedPhotoGallery.getCmsInfo().getCmsId() + "_" + savedPhotoGallery.getPhotoGalleryId() + "_"
					+ photoType + "gallery." + fileExtension;
			fileUtilService.writeFileToPath(Folder.PHOTO_GALLERY.name(), request.getFileContent(), imageName);
			photoGallery.setPhotoName(imageName);
		}

		baseResponse.setMessage("Gallery Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updatePhotoGallery(PhotoGalleryUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		PhotoGallery photoGallery = photoGalleryRepository.findByPhotoGalleryIdAndCmsInfo(request.getPhotoGalleryId(),
				cmsInfo);

		if (photoGallery == null) {
			baseResponse.setMessage("No gallery content found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		photoGallery.setPhotoTitle(request.getPhotoTitle());
		photoGallery.setPhotoDetails(request.getPhotoDetails());
		photoGallery.setPhotoSerial(request.getPhotoSerial());
		
		if (request.isFileSaveOrEditable()) {
			
			String[] photoTypes = photoGallery.getPhotoType().split(" ");
			
			String photoType = "";
			
			for(String pType : photoTypes) {
				
				photoType += pType + "_";			
			}
			
			
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String imageName = photoGallery.getCmsInfo().getCmsId() + "_" + photoGallery.getPhotoGalleryId() + "_"
					+ photoType + "gallery." + fileExtension;
			fileUtilService.writeFileToPath(Folder.PHOTO_GALLERY.name(), request.getFileContent(), imageName);
			photoGallery.setPhotoName(imageName);
		}


		baseResponse.setMessage("Gallery content Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 06-07-2020
	 * @param photoGalleryId
	 * @param file
	 * @return
	 */
	public BaseResponse updateSinglePhotoGallery(Long photoGalleryId, MultipartFile file) {

		BaseResponse baseResponse = new BaseResponse();
	
		PhotoGallery photoGallery = photoGalleryRepository.findById(photoGalleryId).get();

		if (photoGallery == null) {
			baseResponse.setMessage("No gallery photo Found");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		String[] fileExtension = fileUtilService.provideFileExtension(file);
		String imageName = photoGallery.getCmsInfo().getCmsId() + "_" + photoGallery.getPhotoGalleryId() + "_"
				+ photoGallery.getPhotoType() + "_" + "gallery." + fileExtension[1];
		fileUtilService.imageUpload(Folder.PHOTO_GALLERY.name(), imageName, file);
		photoGallery.setPhotoName(imageName);
		photoGalleryRepository.save(photoGallery);

		baseResponse.setMessage("Gallery photo Successfully Uploaded.");
		baseResponse.setMessageType(1);
		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 02-07-2020
	 * @param photoGalleryId
	 * @return
	 */
	@Transactional
	public BaseResponse deletePhotoGallery(Long photoGalleryId) {

		BaseResponse baseResponse = new BaseResponse();

		PhotoGallery photoGallery = photoGalleryRepository.findById(photoGalleryId).get();

		if (photoGallery == null) {
			baseResponse.setMessage("No gallery content found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		photoGalleryRepository.delete(photoGallery);
		fileUtilService.deleteFile(Folder.PHOTO_GALLERY.name(), photoGallery.getPhotoName());
		
		baseResponse.setMessage("Gallery content Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen @since 02-07-2020 @param cmsId @return @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findGalleryContentList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<PhotoGallery> photoGalleries = photoGalleryRepository.findByCmsInfo_CmsIdOrderByPhotoSerialAsc(cmsId);

		List<PhotoGalleryViewResponse> responses = new ArrayList<>();

		for (PhotoGallery info : photoGalleries) {
			PhotoGalleryViewResponse response = new PhotoGalleryViewResponse();
			copyPhotoGalleriesToPhotoGalleriesViewResponse(info, response);

			if (!StringUtils.isEmpty(info.getPhotoName())) {
				try {
					response.setFileContent(
							fileUtilService.fetchFileInByte(Folder.PHOTO_GALLERY.name(), info.getPhotoName()));
					response.setFileName(info.getPhotoName());
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

	public void copyPhotoGalleriesToPhotoGalleriesViewResponse(PhotoGallery photoGallery,
			PhotoGalleryViewResponse viewResponse) {

		viewResponse.setPhotoGalleryId(photoGallery.getPhotoGalleryId());
		viewResponse.setPhotoSerial(photoGallery.getPhotoSerial());
		viewResponse.setPhotoTitle(photoGallery.getPhotoTitle());
		viewResponse.setPhotoDetails(photoGallery.getPhotoDetails());
		viewResponse.setPhotoType(photoGallery.getPhotoType());
		viewResponse.setFileName(photoGallery.getPhotoName());

	}

	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param cmsId
	 * @param photoType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findTypeWiseGalleryPhoto(Long cmsId, String photoType) {

		ItemResponse itemResponse = new ItemResponse();

		List<PhotoGallery> photoGalleries = photoGalleryRepository
				.findByCmsInfo_CmsIdAndPhotoTypeOrderByPhotoSerialAsc(cmsId, photoType);

		List<PhotoGalleryViewResponse> responses = new ArrayList<>();

		for (PhotoGallery info : photoGalleries) {
			PhotoGalleryViewResponse response = new PhotoGalleryViewResponse();
			copyPhotoGalleriesToPhotoGalleriesViewResponse(info, response);

			if (!StringUtils.isEmpty(info.getPhotoName())) {
				try {
					response.setFileContent(
							fileUtilService.fetchFileInByte(Folder.PHOTO_GALLERY.name(), info.getPhotoName()));
					response.setFileName(info.getPhotoName());
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
	
	/**
	 * @author riaz_netizen
	 * @since 22-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findGalleryPhotoQuantity(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<Object[]> list = photoGalleryRepository.findGalleryPhotoQuantity(cmsId);
		
		 Map<String, Object> photo = new LinkedHashMap<>();

		for (Object[] obj : list) {
			 if (obj[0].toString().equalsIgnoreCase("Home Slider")) {
				 photo.put("homeSlider", obj[1]);
			}			 
			 if (obj[0].toString().equalsIgnoreCase("Gallery Photo")) {
				 photo.put("galleryPhoto", obj[1]);
			}			 
			 if (obj[0].toString().equalsIgnoreCase("History")) {
				 photo.put("history", obj[1]);
			}
			 if (obj[0].toString().equalsIgnoreCase("Infrastructure")) {
				 photo.put("infrastructure", obj[1]);
			}
			 if (obj[0].toString().equalsIgnoreCase("Play Ground")) {
				 photo.put("playGround", obj[1]);
			}
			 if (obj[0].toString().equalsIgnoreCase("Computer Lab")) {
				 photo.put("computerLab", obj[1]);
			}
			 if (obj[0].toString().equalsIgnoreCase("Library")) {
				 photo.put("library", obj[1]);
			}
			 if (obj[0].toString().equalsIgnoreCase("Class Room")) {
				 photo.put("classRoom", obj[1]);
			}
			 
		}

		itemResponse.setItem(photo);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

}
