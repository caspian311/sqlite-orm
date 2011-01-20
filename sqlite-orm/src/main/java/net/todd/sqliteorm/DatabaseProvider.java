package net.todd.sqliteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseProvider {
	private final Context context;
	private final String databaseFilename;

	public DatabaseProvider(Context context, String databaseFilename) {
		this.context = context;
		this.databaseFilename = databaseFilename;
	}

	public SQLiteDatabase getDatabase(final Class<?> type) {
		return new SQLiteOpenHelper(context, databaseFilename, null, 1) {
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				String createTableSQL = CreateTableSqlFactory.createTableSql(type);
				db.execSQL(createTableSQL);
			}

		}.getWritableDatabase();
	}
}
