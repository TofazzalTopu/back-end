package com.netizen.cms.api.menuconfig.repository;

import com.netizen.cms.api.menuconfig.model.entity.CmsMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsMenuItemRepository extends JpaRepository<CmsMenuItem, Long> {

    List<CmsMenuItem> findAllByParentCmsMenuItemOrderByNameAsc(CmsMenuItem cmsMenuItem);
    List<CmsMenuItem> findAllByEnableStatus(int status);
}
