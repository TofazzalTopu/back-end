package com.netizen.cms.api.official.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netizen.cms.api.common.response.ItemResponse;
import com.netizen.cms.api.official.service.AboutusInfoService;
import com.netizen.cms.api.official.service.EventLogService;
import com.netizen.cms.api.official.service.MemberInfoService;
import com.netizen.cms.api.official.service.NoticeInfoService;
import com.netizen.cms.api.official.service.SpeechInfoService;

@RestController
@RequestMapping("/public")
public class PublicOfficialController {
	
	@Autowired
	private SpeechInfoService speechInfoService;
	@Autowired
	private NoticeInfoService noticeInfoService;
	@Autowired
	private EventLogService eventLogService;
	@Autowired
	private AboutusInfoService aboutusInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	
	@GetMapping(value = "/welcome/speechs")
	public ResponseEntity<?> findWelcomeSpeech(@RequestParam Long cmsId) {
			
		return new ResponseEntity<>(speechInfoService.findWelcomeSpeech(cmsId), HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 19-07-2020
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/speechs")
	public ResponseEntity<ItemResponse> speechInfoList(@RequestParam Long cmsId){
		ItemResponse itemResponse=speechInfoService.showSpeechInfoList(cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}
	
	@GetMapping(value = "/notices")
	public ResponseEntity<?> findNotices(@RequestParam Long cmsId) {			
		return new ResponseEntity<>(noticeInfoService.findEnableNoticeInfoList(cmsId), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/events")
	public ResponseEntity<?> findEvents(@RequestParam Long cmsId) {			
		return new ResponseEntity<>(eventLogService.showEventLogList(cmsId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/aboutus")
	public ResponseEntity<?> findAboutus(@RequestParam Long cmsId) {			
		return new ResponseEntity<>(aboutusInfoService.findAboutusInfos(cmsId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/aboutus/by/type")
	public ResponseEntity<?> findAboutusByType(@RequestParam Long cmsId, @RequestParam String aboutusType) {			
		return new ResponseEntity<>(aboutusInfoService.findAboutusInfoByType(cmsId, aboutusType), HttpStatus.OK);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 07-07-2020
	 * @param noticeId
	 * @return
	 */
	@GetMapping(value = "/notice/file/find")
	public ResponseEntity<?> findNoticeFile(@RequestParam Long noticeId){
		return new ResponseEntity<>(noticeInfoService.findNoticeFile(noticeId),HttpStatus.FOUND);
	}
	
	/**
	 * @author riaz_netizen
	 * @since 08-07-2020
	 * @param memberType
	 * @param cmsId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/typeWise/members")
	public ResponseEntity<ItemResponse> getTypeWiseMemberList(@RequestParam String memberType,@RequestParam Long cmsId){
		ItemResponse itemResponse=memberInfoService.findTypeWiseMemberListForClient(memberType, cmsId);
		return new ResponseEntity<>(itemResponse,HttpStatus.OK);
	}

}
