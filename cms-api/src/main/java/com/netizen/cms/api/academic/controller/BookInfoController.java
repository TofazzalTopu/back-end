package com.netizen.cms.api.academic.controller;

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
import com.netizen.cms.api.academic.model.request.BookInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.BookInfoUpdateRequest;
import com.netizen.cms.api.academic.service.BookInfoService;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;

@RestController
@RequestMapping("/book/info")
public class BookInfoController {

	@Autowired
	public BookInfoService bookInfoService;

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param requesSave
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<BaseResponse> saveBookInfo(@RequestBody @Valid BookInfoSaveRequest requesSave) {
		BaseResponse baseResponse = bookInfoService.saveBookInfo(requesSave);
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param requestUpdate
	 * @return
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<BaseResponse> updateBookInfo(@RequestBody @Valid BookInfoUpdateRequest requestUpdate) {
		BaseResponse baseResponse = bookInfoService.updateBookInfo(requestUpdate);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param bookId
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseResponse> deleteBookInfo(@RequestParam Long bookId) {
		BaseResponse baseResponse = bookInfoService.deleteBookInfo(bookId);
		return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * @author riaz_netizen
	 * @param cmsId
	 * @since 05-07-2020
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/list")
	public ResponseEntity<ItemResponse> getBookInfoList(@RequestParam Long cmsId){
		//UserInfoUtils.getLoggedInICmsId()
		ItemResponse itemResponse=bookInfoService.searchBookInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
}
