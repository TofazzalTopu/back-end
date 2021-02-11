package com.netizen.cms.api.menuconfig.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMenuConfigSaveRequest {

    @NotNull(message = "CMS Info ID can not be null!")
    private Long cmsInfoId;

    @NotNull(message = "CMS Menu Item ID can not be null!")
    private List<Long> cmsMenuItemIds;

    @NotBlank(message = "Name can not be null!")
    private String assign_user_id;

}
