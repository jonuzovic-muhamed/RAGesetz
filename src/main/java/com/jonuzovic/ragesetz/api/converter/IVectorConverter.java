package com.jonuzovic.ragesetz.api.converter;

import java.util.List;

public interface IVectorConverter {
	
	String convertToDatabaseColumn(List<Float> list);

	List<Float> convertToEntityAttribute(String dbData);
}
