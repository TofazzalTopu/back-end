package com.netizen.cms.api.gallery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import com.netizen.cms.api.gallery.model.entity.PhotoGallery;

public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery, Long> {

	public PhotoGallery findByPhotoGalleryIdAndCmsInfo(Long photoGalleryId, CmsInfo cmsInfo);

	List<PhotoGallery> findByCmsInfo_CmsIdOrderByPhotoSerialAsc(Long cmsId);

	List<PhotoGallery> findByCmsInfo_CmsIdAndPhotoTypeOrderByPhotoSerialAsc(Long cmsId, String photoType);

	@Query(value = "SELECT photo_type,count(photo_gallery_id) as imgfound FROM photo_gallery where cms_id=:cmsId group by photo_type", nativeQuery = true)
    public List<Object[]> findGalleryPhotoQuantity(@Param("cmsId") Long cmsId);
    

}
