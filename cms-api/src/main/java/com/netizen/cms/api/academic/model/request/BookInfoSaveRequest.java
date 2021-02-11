package com.netizen.cms.api.academic.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoSaveRequest {

	@NotBlank
	private String bookType;

	@NotBlank
	private String bookName;

	private Double bookPrice;

	@NotBlank
	private String authorName;

	@NotBlank
	private String publicationName;

	@NotBlank
	private String publicationYear;

	@NotNull
	private Long classId;
	
	@NotNull
	private Long cmsId;
	
}
