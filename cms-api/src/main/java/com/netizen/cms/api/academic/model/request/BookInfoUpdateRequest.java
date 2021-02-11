package com.netizen.cms.api.academic.model.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoUpdateRequest {

	@NotNull
	private Long bookId;

	private String bookType;

	private String bookName;

	private Double bookPrice;

	private String authorName;

	private String publicationName;

	private String publicationYear;
	
	private Long classId;
	
	@NotNull
	private Long cmsId;

}
