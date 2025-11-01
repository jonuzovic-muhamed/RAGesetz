package com.jonuzovic.ragesetz.infrastructure.persistence.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jonuzovic.ragesetz.core.converter.IVectorConverter;

@SpringBootTest
class ListToVectorConverterUnitTest {
	
	IVectorConverter converter;
	
	@Autowired
	ListToVectorConverterUnitTest(IVectorConverter converter) {
		this.converter = converter;
	}

	static final int VECTOR_DIM = 1536;

	static List<Float> asList = new ArrayList<Float>();
	static float[] asArray = new float[VECTOR_DIM];
	static String asString = new String();

	@BeforeAll
	static void setupVectorData() {
		Random random = new Random();
		asString += "[";
		for (int i = 0; i < VECTOR_DIM; i++) {
			float value = random.nextFloat();
			asList.add(value);
			asArray[i] = value;
			if (i == VECTOR_DIM - 1) {
				asString += value;
			} else {
				asString += value + ",";				
			}
		}
		asString += "]";
	}

	@Test void testConvertToDatabaseColumn() { 
		assertEquals(asString, converter.convertToDatabaseColumn(asList));
	}
	
	@Test
	void testConvertToDatabaseColumnWithNull() {
		assertEquals("[]", converter.convertToDatabaseColumn(null));
	}
	
	@Test
	void testConvertToDatabaseColumnWithEmptyList() {
		assertEquals("[]", converter.convertToDatabaseColumn(List.of()));
	}
	
	@Test
	void testConvertToEntityAttribute() {
		assertEquals(asList, converter.convertToEntityAttribute(asString));
		assertEquals(asList.get(VECTOR_DIM - 1), converter.convertToEntityAttribute(asString).get(VECTOR_DIM - 1));
	}
	
	@Test
	void testConvertToEntityAttributeWithNull() {
		assertEquals(List.of(), converter.convertToEntityAttribute(null));
	}
	
	@Test
	void testConvertToEntityAttributeWithEmptyString() {
		assertEquals(List.of(), converter.convertToEntityAttribute(""));
	}
	
	@Test
	void testConvertToEntityAttributeWithWrongFormatVector() {
		assertEquals(List.of(), converter.convertToEntityAttribute("0.3213,1.000,4.324]"));
		assertEquals(List.of(), converter.convertToEntityAttribute("[0.3213,1.000,4.324"));
		assertEquals(List.of(), converter.convertToEntityAttribute("0.3213,1.000,4.324"));
		assertEquals(List.of(), converter.convertToEntityAttribute("[0.3213,1.000,4.324,]"));
	}
	
	@Test
	void testConvertToEntityAttributeWithTooShortVector() {
		assertEquals(List.of(), converter.convertToEntityAttribute("[0.3213,1.000,4.324]"));
	}
	
	@Test
	void testConvertToEntityAttributeWithTooManyCommas() {
		StringBuilder wrongFormat = new StringBuilder();
		wrongFormat.append(asString);
		wrongFormat.deleteCharAt(wrongFormat.length()-1);
		wrongFormat.append(",,,,]");
		assertEquals(List.of(), converter.convertToEntityAttribute(wrongFormat.toString()));
	}
	
}
