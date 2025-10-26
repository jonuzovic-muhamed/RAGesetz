package com.jonuzovic.ragesetz.core.converter;

import java.util.List;

public interface IVectorConverter {
	
	String convertToDatabaseColumn(List<Float> list);

	List<Float> convertToEntityAttribute(String dbData);
}
