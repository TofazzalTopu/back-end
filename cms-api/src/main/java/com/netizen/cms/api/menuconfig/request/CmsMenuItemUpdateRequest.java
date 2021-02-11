package com.netizen.cms.api.menuconfig.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuItemUpdateRequest {

    @NotBlank(message = "Assign User ID can not be null!")
    private Long menuItemId;

    @NotBlank(message = "Assign User ID can not be null!")
    private String name;

    @NotBlank(message = "Assign User ID can not be null!")
    private String defaultCode;

    @NotNull(message = "Assign User ID can not be null!")
    private int enableStatus;

    @NotNull(message = "Assign User ID can not be null!")
    private int parentStatus;

    private Long parentCmsMenuItemId;

    @NotNull(message = "Assign User ID can not be null!")
    private int menuLayer;

    private String note;
}
