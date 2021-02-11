package com.netizen.cms.api.official.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.official.model.entity.SpeechInfo;


public interface SpeechInfoRepository extends JpaRepository<SpeechInfo, Long> {
	
	public SpeechInfo findBySpeechIdAndCmsInfo(Long speechId, CmsInfo cmsInfo);
	
	public List<SpeechInfo> findByCmsInfoOrderBySpeechSerialAsc(CmsInfo cmsInfo);

	public SpeechInfo findTopByWelcomeSpeechStatusOrderBySpeechIdDesc(int i);
	
	public List<SpeechInfo> findByCmsInfo_CmsIdAndSpeechStatusAndWelcomeSpeechStatusOrderBySpeechSerial(Long cmsId, int speechStatus, int welcomeSpeechStatus);

}
