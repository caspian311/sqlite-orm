package net.todd.sqliteorm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldName {
	public static final String TEXT = "TEXT";
	public static final String INTEGER = "INTEGER";
	public static final String REAL = "REAL";

	private static final String GETTER_PREFIX = "get";
	private static final Map<Class<?>, String> classToTypeMap = new HashMap<Class<?>, String>();
	static {
		classToTypeMap.put(String.class, TEXT);
		classToTypeMap.put(Integer.class, INTEGER);
		classToTypeMap.put(int.class, INTEGER);
		classToTypeMap.put(long.class, INTEGER);
		classToTypeMap.put(Long.class, INTEGER);
		classToTypeMap.put(Float.class, REAL);
		classToTypeMap.put(float.class, REAL);
	}

	public static Map<String, String> getFieldMap(Class<?> type) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		for (Method method : type.getMethods()) {
			if (method.getName().startsWith(GETTER_PREFIX)) {
				String fieldName = extractFieldName(method);
				String fieldType = extractFieldType(method);
				fieldMap.put(fieldName, fieldType);
			}
		}

		return fieldMap;
	}

	private static String extractFieldType(Method method) {
		return classToTypeMap.get(method.getReturnType());
	}

	private static String extractFieldName(Method method) {
		String fieldName = method.getName().substring(GETTER_PREFIX.length());
		fieldName = fieldName.substring(0, 1).toLowerCase()
				+ fieldName.substring(1, fieldName.length());
		return fieldName;
	}

	public static List<String> getSortedFieldNames(Class<?> type) {
		List<String> fieldNames = new ArrayList<String>(getFieldMap(type).keySet());
		Collections.sort(fieldNames);
		return fieldNames;
	}
}
