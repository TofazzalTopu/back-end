package com.netizen.cms.api.academic.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netizen.cms.api.academic.model.entity.BookInfo;
import com.netizen.cms.api.academic.model.entity.ClassInfo;
import com.netizen.cms.api.academic.model.request.BookInfoSaveRequest;
import com.netizen.cms.api.academic.model.request.BookInfoUpdateRequest;
import com.netizen.cms.api.academic.model.response.BookInfoViewResponse;
import com.netizen.cms.api.academic.repository.BookInfoRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.common.response.BaseResponse;
import com.netizen.cms.api.common.response.ItemResponse;


@Service
public class BookInfoService {

	@Autowired
	private BookInfoRepository bookInfoRepository;

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse saveBookInfo(BookInfoSaveRequest request) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessageType(0);
		baseResponse.setMessage("Something went wrong");
		

		Long cmsId = request.getCmsId();

		BookInfo bookInfo = new BookInfo();

		bookInfo.setCmsInfo(CmsInfo.builder().cmsId(cmsId).build());
		bookInfo.setClassInfo(ClassInfo.builder().classId(request.getClassId()).build());
		bookInfo.setBookType(request.getBookType());
		bookInfo.setBookName(request.getBookName());
		bookInfo.setBookPrice(request.getBookPrice());
		bookInfo.setAuthorName(request.getAuthorName());
		bookInfo.setPublicationName(request.getPublicationName());
		bookInfo.setPublicationYear(request.getPublicationYear());

		bookInfoRepository.save(bookInfo);

		baseResponse.setMessage("Book Info Successfully Saved.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param request
	 * @return
	 */
	@Transactional
	public BaseResponse updateBookInfo(BookInfoUpdateRequest request) {

		BaseResponse baseResponse = new BaseResponse();

		CmsInfo cmsInfo = CmsInfo.builder().cmsId(request.getCmsId()).build();

		BookInfo bookInfo = bookInfoRepository.findByBookIdAndCmsInfo(request.getBookId(), cmsInfo);

		if (bookInfo == null) {
			baseResponse.setMessage("No book found to update.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		bookInfo.setClassInfo(ClassInfo.builder().classId(request.getClassId()).build());
		bookInfo.setBookType(request.getBookType());
		bookInfo.setBookName(request.getBookName());
		bookInfo.setBookPrice(request.getBookPrice());
		bookInfo.setAuthorName(request.getAuthorName());
		bookInfo.setPublicationName(request.getPublicationName());
		bookInfo.setPublicationYear(request.getPublicationYear());

		baseResponse.setMessage("Book Info Successfully Updated.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param bookId
	 * @return
	 */
	@Transactional
	public BaseResponse deleteBookInfo(Long bookId) {

		BaseResponse baseResponse = new BaseResponse();

		BookInfo bookInfo = bookInfoRepository.findById(bookId).get();

		if (bookInfo == null) {
			baseResponse.setMessage("No book found to delete.");
			baseResponse.setMessageType(0);
			return baseResponse;
		}

		bookInfoRepository.delete(bookInfo);

		baseResponse.setMessage("Book Info Successfully Deleted.");
		baseResponse.setMessageType(1);

		return baseResponse;
	}

	/**
	 * @author riaz_netizen
	 * @since 05-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchBookInfoList(Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<BookInfo> bookInfos = bookInfoRepository.findByCmsInfo_cmsIdOrderByClassInfo_ClassIdAsc(cmsId);

		List<BookInfoViewResponse> responses = new ArrayList<>();

		for (BookInfo info : bookInfos) {
			BookInfoViewResponse response = new BookInfoViewResponse();
			copyBookInfoToBookInfoViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

	public void copyBookInfoToBookInfoViewResponse(BookInfo bookInfo, BookInfoViewResponse bookInfoViewResponse) {

		bookInfoViewResponse.setBookId(bookInfo.getBookId());
		bookInfoViewResponse.setClassId(bookInfo.getClassInfo().getClassId());
		bookInfoViewResponse.setClassName(bookInfo.getClassInfo().getClassName());
		bookInfoViewResponse.setBookType(bookInfo.getBookType());
		bookInfoViewResponse.setBookName(bookInfo.getBookName());
		bookInfoViewResponse.setAuthorName(bookInfo.getAuthorName());
		bookInfoViewResponse.setPublicationName(bookInfo.getPublicationName());
		bookInfoViewResponse.setPublicationYear(bookInfo.getPublicationYear());
		bookInfoViewResponse.setBookPrice(bookInfo.getBookPrice());

	}
	
	
	/**
	 * @author riaz_netizen
	 * @since 19-07-2020
	 * @param classId
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse searchClassWiseBookList(Long classId,Long cmsId) {

		ItemResponse itemResponse = new ItemResponse();

		List<BookInfo> bookInfos = bookInfoRepository.findByClassInfo_ClassIdAndCmsInfo_cmsId(classId, cmsId);

		List<BookInfoViewResponse> responses = new ArrayList<>();

		for (BookInfo info : bookInfos) {
			BookInfoViewResponse response = new BookInfoViewResponse();
			copyBookInfoToBookInfoViewResponse(info, response);
			responses.add(response);
		}

		itemResponse.setItem(responses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);

		return itemResponse;
	}

}
