package net.todd.sqliteorm;

import java.util.List;

public class UpdateSqlFactory {
	public static String createUpdateSQL(Class<?> type) {
		StringBuilder sb = new StringBuilder();
		Object tableName = TableName.getTableNameFromClass(type);
		sb.append("update ").append(tableName).append(" set ");
		List<String> sortedKeys = FieldName.getSortedFieldNames(type);
		sortedKeys.remove("id");
		for (int i = 0; i < sortedKeys.size(); i++) {
			String key = sortedKeys.get(i);
			sb.append(key).append("=?");
			if (i < sortedKeys.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(" where id=?");
		return sb.toString();
	}
}
