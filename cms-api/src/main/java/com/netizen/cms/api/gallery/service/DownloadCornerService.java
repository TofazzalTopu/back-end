package com.netizen.cms.api.gallery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.enums.Folder;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.common.util.FileUtilService;
import com.netizen.cms.api.gallery.model.entity.DownloadCorner;
import com.netizen.cms.api.gallery.model.request.DownloadCornerSaveRequest;
import com.netizen.cms.api.gallery.model.request.DownloadCornerUpdateRequest;
import com.netizen.cms.api.gallery.model.response.DownloadCornerViewResponse;
import com.netizen.cms.api.gallery.repository.DownloadCornerRepository;

@Service
public class DownloadCornerService {

	@Autowired
	private DownloadCornerRepository downloadCornerRepository;
	@Autowired
	private FileUtilService fileUtilService;

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveDownloadCorner(DownloadCornerSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		DownloadCorner downloadCorner = new DownloadCorner();
		downloadCorner.setCmsInfo(cmsInfo);
		downloadCorner.setFileSerial(request.getFileSerial());
		downloadCorner.setFileTitle(request.getFileTitle());
		downloadCorner.setFileCreateDate(new Date());

		DownloadCorner savedDownloadCorner = downloadCornerRepository.save(downloadCorner);

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = downloadCorner.getCmsInfo().getCmsId() + "_" + savedDownloadCorner.getFileId() + "_"
					+ "downloadcorner." + fileExtension;
			fileUtilService.writeFileToPath(Folder.DOWNLOAD_CORNER.name(), request.getFileContent(), fileName);
			downloadCorner.setFileName(fileName);
		}

		baseResponse.setMessage("DownloadCorner Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateDownloadCorner(DownloadCornerUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		DownloadCorner downloadCorner = downloadCornerRepository.findById(request.getFileId()).get();

		if (downloadCorner == null) {
			baseResponse.setMessage("No file found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		downloadCorner.setFileSerial(request.getFileSerial());
		downloadCorner.setFileTitle(request.getFileTitle());

		if (request.isFileSaveOrEditable()) {
			String fileExtension = fileUtilService.provideFileExtension(request.getFileName()).get();
			String fileName = downloadCorner.getCmsInfo().getCmsId() + "_" + downloadCorner.getFileId() + "_"
					+ "downloadcorner." + fileExtension;
			fileUtilService.writeFileToPath(Folder.DOWNLOAD_CORNER.name(), request.getFileContent(), fileName);
			downloadCorner.setFileName(fileName);
		}

		downloadCornerRepository.save(downloadCorner);

		baseResponse.setMessage("DownloadCorner File Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 06-07-2020
	 * @param fileId
	 * @param file
	 * @return
	 *//*
		 * public BaseResponse updateSingleDownloadCorner(Long fileId, MultipartFile
		 * file) {
		 * 
		 * BaseResponse baseResponse = new BaseResponse(); DownloadCorner downloadCorner
		 * = downloadCornerRepository.findById(fileId).get(); if (downloadCorner ==
		 * null) { baseResponse.setMessage("No File Found");
		 * baseResponse.setMessageType(0); return baseResponse; }
		 * 
		 * String[] fileExtension = fileUtilService.provideFileExtension(file); String
		 * imageName = downloadCorner.getCmsInfo().getCmsId() + "_" +
		 * downloadCorner.getFileId() + "_" + "downloadcorner." + fileExtension[1];
		 * fileUtilService.fileUpload(Folder.DOWNLOAD_CORNER.name(), imageName, file);
		 * downloadCorner.setFileName(imageName);
		 * downloadCornerRepository.save(downloadCorner);
		 * 
		 * baseResponse.setMessage("Download Corner File Successfully Uploaded.");
		 * baseResponse.setMessageType(1); return baseResponse; }
		 */

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @param fileId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteDownloadCorner(Long fileId) {

		BaseResponse baseResponse = new BaseResponse();

		DownloadCorner downloadCorner = downloadCornerRepository.findById(fileId).get();

		if (downloadCorner == null) {
			baseResponse.setMessage("No downloadCorner file found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		downloadCornerRepository.delete(downloadCorner);
		fileUtilService.deleteFile(Folder.DOWNLOAD_CORNER.name(), downloadCorner.getFileName());

		baseResponse.setMessage("DownloadCorner file Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 01-07-2020
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchDownloadCornerList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<DownloadCorner> importantLinkInfos = downloadCornerRepository
				.findByCmsInfo_cmsIdOrderByFileSerialAsc(cmsId);

		List<DownloadCornerViewResponse> responses = new ArrayList<>();

		for (DownloadCorner info : importantLinkInfos) {
			DownloadCornerViewResponse response = new DownloadCornerViewResponse();
			copyDownloadCornerToDownloadCorneroViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public void copyDownloadCornerToDownloadCorneroViewResponse(DownloadCorner downloadCorner,
			DownloadCornerViewResponse cornerViewResponse) {

		cornerViewResponse.setFileId(downloadCorner.getFileId());
		cornerViewResponse.setFileTitle(downloadCorner.getFileTitle());
		cornerViewResponse.setFileSerial(downloadCorner.getFileSerial());
		cornerViewResponse.setFileName(downloadCorner.getFileName());

	}

	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param fileId
	 * @return
	 */
	public Map<String, Object> findDownloadCornerFile(Long fileId) {

		Optional<DownloadCorner> downloadCornerOpt = downloadCornerRepository.findById(fileId);

		Map<String, Object> map = new HashMap<>();
		map.put("fileFound", false);

		DownloadCorner downloadCorner = null;

		if (downloadCornerOpt.isPresent()) {
			downloadCorner = downloadCornerOpt.get();
		}

		try {

			if (!StringUtils.isEmpty(downloadCorner.getFileName())) {
				map.put("fileFound", true);
				map.put("file",
						fileUtilService.fetchFileInByte(Folder.DOWNLOAD_CORNER.name(), downloadCorner.getFileName()));
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
