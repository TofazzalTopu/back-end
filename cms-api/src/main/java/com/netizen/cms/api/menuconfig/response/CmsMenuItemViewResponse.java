package com.netizen.cms.api.menuconfig.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuItemViewResponse {

    private Long menuItemId;

    private String name;

    private String defaultCode;

    private int enableStatus;

    private int parentStatus;

    private Long parentCmsMenuItemId;

    private int menuLayer;

}
