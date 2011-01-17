package net.todd.sqliteorm;

import android.content.Context;

public class DatabaseHelperFactory {
	public <T> DatabaseHelper<T> create(Context context, String databaseFilename, Class<T> type) {
		return new DatabaseHelper<T>(context, databaseFilename, type);
	}
}
