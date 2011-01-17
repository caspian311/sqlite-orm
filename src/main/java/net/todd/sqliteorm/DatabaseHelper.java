package net.todd.sqliteorm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper<T> {
	private final Class<T> type;
	private DatabaseProvider databaseProvider;

	public DatabaseHelper(Context context, String databaseFilename, Class<T> type) {
		this(type, new DatabaseProvider(context, databaseFilename));
	}

	DatabaseHelper(final Class<T> type, DatabaseProvider databaseProvider) {
		this.type = type;
		this.databaseProvider = databaseProvider;
	}

	public long createObject() {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		String insertSQL = InsertSqlFactory.createInsertSQL(type);
		SQLiteStatement insertStatement = database.compileStatement(insertSQL);
		long id = insertStatement.executeInsert();
		return id;
	}

	public void updateObject(Map<String, Object> storedValues) {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		String updateSql = UpdateSqlFactory.createUpdateSQL(type);
		SQLiteStatement compileStatement = database.compileStatement(updateSql);
		List<String> fields = FieldName.getSortedFieldNames(type);
		fields.remove("id");
		for (int i = 0; i < fields.size(); i++) {
			Object field = fields.get(i);
			Object value = storedValues.get(field);
			if (value.getClass().isAssignableFrom(String.class)) {
				compileStatement.bindString(i + 1, String.class.cast(value));
			} else if (value.getClass().isAssignableFrom(Double.class)) {
				compileStatement.bindDouble(i + 1, Double.class.cast(value));
			} else if (value.getClass().isAssignableFrom(Long.class)) {
				compileStatement.bindLong(i + 1, Long.class.cast(value));
			}
		}
		compileStatement.bindLong(fields.size() + 1, Long.class.cast(storedValues.get("id")));
		compileStatement.execute();
	}

	public Map<String, Object> loadObject(long id) {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		Cursor cursor = database.query(TableName.getTableNameFromClass(type), null, null, null,
				null, null, null);

		List<Map<String, Object>> allStoredValues = getStoredValuesFromCursor(cursor);

		return allStoredValues.isEmpty() ? Collections.<String, Object> emptyMap()
				: allStoredValues.get(0);
	}

	private List<Map<String, Object>> getStoredValuesFromCursor(Cursor cursor) {
		List<Map<String, Object>> allStoredValues = new ArrayList<Map<String, Object>>();

		if (cursor.moveToFirst()) {
			do {
				Map<String, Object> storedValues = new HashMap<String, Object>();

				Map<String, String> fieldMap = FieldName.getFieldMap(type);
				String[] columnNames = cursor.getColumnNames();
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					Object value = null;

					if (FieldName.TEXT.equals(fieldMap.get(columnName))) {
						value = cursor.getString(i);
					} else if (FieldName.REAL.equals(fieldMap.get(columnName))) {
						value = cursor.getDouble(i);
					} else if (FieldName.INTEGER.equals(fieldMap.get(columnName))) {
						value = cursor.getInt(i);
					}

					storedValues.put(columnName, value);
				}

				allStoredValues.add(storedValues);
			} while (cursor.moveToNext());
		}

		return allStoredValues;
	}

	public List<Map<String, Object>> find(String query) {
		return null;
	}

	public List<Map<String, Object>> getAll() {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		Cursor cursor = database.query(TableName.getTableNameFromClass(type), null, null, null,
				null, null, null);
		return getStoredValuesFromCursor(cursor);
	}

	public void delete(Long id) {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		database.delete(TableName.getTableNameFromClass(type), "id=?", new String[] { "" + id });
	}

	public void deleteAll() {
		SQLiteDatabase database = databaseProvider.getDatabase(type);
		database.delete(TableName.getTableNameFromClass(type), null, null);
	}
}
