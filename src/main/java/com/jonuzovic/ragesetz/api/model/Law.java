package com.jonuzovic.ragesetz.api.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Law {
	private Long id;
	private String lawCode;
	private String sectionNumber;
	private String lawTitle;
	private String lawContent;
	private List<Float> lawEmbedding;
	private String sourceUrl;
	private Timestamp createdAt;
}