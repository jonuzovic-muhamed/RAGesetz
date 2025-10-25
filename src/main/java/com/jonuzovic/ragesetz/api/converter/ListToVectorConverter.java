package com.jonuzovic.ragesetz.api.converter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListToVectorConverter implements IVectorConverter {

    public String convertToDatabaseColumn(List<Float> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < list.size(); i++) {
        	if (i == list.size() - 1) {
                stringBuilder.append(list.get(i));
                break;
        	}
            stringBuilder.append(list.get(i));
            stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public List<Float> convertToEntityAttribute(String dbData) {
        if ("[]".equals(dbData) || dbData == null || dbData.isBlank()
        		|| !dbData.startsWith("[") || !dbData.endsWith("]") || dbData.contains(",,")) 
        	return List.of();
        
        String cleaned = dbData.replace("[", "").replace("]", "");
        String[] parts = cleaned.split(",");
        
        if (parts.length != 1536) {
        	return List.of();
        }
        
        List<Float> result = new ArrayList<>();
        for (String part : parts) {
            result.add(Float.parseFloat(part.trim()));
        }
        
        return result;
    }
}