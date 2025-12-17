package com.jonuzovic.ragesetz.core.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString(exclude = "id, lawEmbedding, createdAt")
public class Law {
	private Long id;
	private String lawCode;
	private String lawSectionNumber;
	private String lawTitle;
	private String lawContent;
	private List<Float> lawEmbedding;
	private String sourceUrl;
	private Timestamp createdAt;
}