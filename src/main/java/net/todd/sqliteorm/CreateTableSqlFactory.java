package net.todd.sqliteorm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateTableSqlFactory {
	public static String createTableSql(final Class<?> type) {
		String tableName = TableName.getTableNameFromClass(type);
		Map<String, String> fieldMap = FieldName.getFieldMap(type);
		List<String> fields = new ArrayList<String>(fieldMap.keySet());
		fields.remove("id");
		Collections.sort(fields);

		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(tableName).append(" (");
		for (int i = 0; i < fields.size(); i++) {
			String fieldName = fields.get(i);
			String fieldType = fieldMap.get(fieldName);
			sb.append(fieldName).append(" ").append(fieldType).append(", ");
		}
		sb.append("id INTEGER PRIMARY KEY AUTOINCREMENT");
		sb.append(")");
		return sb.toString();
	}
}
