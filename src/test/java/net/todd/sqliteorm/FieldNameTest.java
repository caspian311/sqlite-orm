package net.todd.sqliteorm;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FieldNameTest {
	@Test
	public void fieldsMapsContainTheFieldNameWithTheirDatabaseTypes() {
		Map<String, String> fields = FieldName.getFieldMap(Car.class);

		assertEquals("TEXT", fields.get("make"));
		assertEquals("TEXT", fields.get("model"));
		assertEquals("INTEGER", fields.get("year"));
		assertEquals("REAL", fields.get("price"));
		assertEquals("INTEGER", fields.get("id"));
	}

	@Test
	public void fieldsAreReturnedInASortedList() {
		List<String> fields = FieldName.getSortedFieldNames(Car.class);

		assertEquals(Arrays.asList("id", "make", "model", "price", "year"), fields);
	}

	public interface Car extends DBObject {
		int getYear();

		void setYear(int year);

		String getMake();

		void setMake(String make);

		String getModel();

		void setModel(String model);

		float getPrice();

		void setPrice(float price);
	}
}
