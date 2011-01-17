package net.todd.sqliteorm;

public class TableName {
	public static String getTableNameFromClass(Class<?> clazz) {
		String tableName = clazz.getName();
		if (clazz.getName().lastIndexOf(".") != -1) {
			tableName = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
			if (tableName.indexOf("$") != -1) {
				tableName = tableName.substring(tableName.indexOf("$") + 1);
			}
		}
		return tableName;
	}
}
