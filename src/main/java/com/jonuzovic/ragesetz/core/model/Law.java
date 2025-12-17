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
public class Law {
	private Long id;
	private String lawCode;
	private String lawSectionNumber;
	private String lawTitle;
	private String lawContent;
	private List<Float> lawEmbedding;
	private String sourceUrl;
	private Timestamp createdAt;

    public String toString () {
        return "{ " + this.lawCode + " " + this.lawSectionNumber + " " + this.lawTitle + " " + this.lawContent + " " + this.sourceUrl + " }";
    }
}