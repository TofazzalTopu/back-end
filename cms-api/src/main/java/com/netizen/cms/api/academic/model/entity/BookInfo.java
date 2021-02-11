package com.netizen.cms.api.academic.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netizen.cms.api.cmsinfo.model.entity.CmsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_info", uniqueConstraints = @UniqueConstraint(columnNames = {"cms_id", "class_id", "book_type", "book_name"}))
public class BookInfo implements Serializable {

	private static final long serialVersionUID = 7664953955800605615L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	@Column(name = "book_type", nullable = false)
	private String bookType;

	@Column(name = "book_name", nullable = false)
	private String bookName;

	@Column(name = "book_price", columnDefinition = "Double default 0")
	private Double bookPrice;

	@Column(name = "author_name", nullable = false)
	private String authorName;

	@Column(name = "publication_name", nullable = false)
	private String publicationName;

	@Column(name = "publication_year", nullable = false)
	private String publicationYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	private ClassInfo classInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cms_id", nullable = false)
	private CmsInfo cmsInfo;

}
