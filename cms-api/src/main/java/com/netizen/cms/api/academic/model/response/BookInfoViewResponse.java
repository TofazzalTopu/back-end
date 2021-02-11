package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoViewResponse {

	private Long bookId;
	
	private String bookType;
	
	private String bookName;
	
	private Double bookPrice;
	
	private String authorName;
	
	private String publicationName;
	
	private String publicationYear;
	
	private Long classId;
	
	private String className;
	
}
