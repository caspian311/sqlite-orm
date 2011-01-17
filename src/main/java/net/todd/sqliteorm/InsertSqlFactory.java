package net.todd.sqliteorm;

import java.util.List;

public class InsertSqlFactory {
	public static String createInsertSQL(Class<?> type) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(TableName.getTableNameFromClass(type)).append(" (")
				.append(columnNamesWithoutId(type)).append(") values (")
				.append(questionMarks(type)).append(")");
		return sb.toString();
	}

	private static String questionMarks(Class<?> type) {
		StringBuilder sb = new StringBuilder();
		int size = FieldName.getSortedFieldNames(type).size();
		size--;
		for (int i = 0; i < size; i++) {
			sb.append("?");
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private static String columnNamesWithoutId(Class<?> type) {
		StringBuilder sb = new StringBuilder();
		List<String> keys = FieldName.getSortedFieldNames(type);
		keys.remove("id");
		for (int i = 0; i < keys.size(); i++) {
			sb.append(keys.get(i));
			if (i < keys.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}
