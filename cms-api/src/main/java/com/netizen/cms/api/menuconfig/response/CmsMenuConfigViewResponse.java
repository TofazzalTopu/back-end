package com.netizen.cms.api.menuconfig.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuConfigViewResponse {

	private Long menuConfigId;

	private Long cmsInfoId;

	private Long cmsMenuItemId;

	private String assign_user_id;
}
