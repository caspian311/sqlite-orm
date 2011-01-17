package net.todd.sqliteorm;

import android.content.Context;

public class DataObjectInvocationHandlerFactory {
	public <T> DataObjectInvocationHandler<T> create(Context context, String databaseFilename,
			Class<T> type) {
		return new DataObjectInvocationHandler<T>(new DatabaseHelper<T>(context, databaseFilename,
				type));
	}
}
