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
public class CmsMenuItemSaveRequest {

    private Long menuItemId;

    @NotBlank(message = "Name can not be null!")
    private String name;

    @NotBlank(message = "Default Code can not be null!")
    private String defaultCode;

    @NotNull(message = "Enable Status can not be null!")
    private int enableStatus;

    @NotNull(message = "Parent Status can not be null!")
    private int parentStatus;

    private Long parentCmsMenuItemId;

    @NotNull(message = "Menu Layer can not be null!")
    private int menuLayer;

    private String note;
}
