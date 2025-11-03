package com.jonuzovic.ragesetz.api.dto;

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
public class LawDto {
	private String lawCode;
	private String lawSectionNumber;
	private String lawTitle;
	private String lawContent;
	private String sourceUrl;
}
