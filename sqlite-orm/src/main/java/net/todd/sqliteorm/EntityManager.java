package net.todd.sqliteorm;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class EntityManager {
	private final String databaseFilename;
	private final Context context;
	private final DataObjectInvocationHandlerFactory dataObjectInvocationHandlerFactory;
	private final DatabaseHelperFactory databaseHelperFactory;

	public EntityManager(Context context, String databaseFilename) {
		this(context, databaseFilename, new DataObjectInvocationHandlerFactory(),
				new DatabaseHelperFactory());
	}

	public EntityManager(Context context, String databaseFilename,
			DataObjectInvocationHandlerFactory dataObjectInvocationHandlerFactory,
			DatabaseHelperFactory databaseHelperFactory) {
		this.context = context;
		this.databaseFilename = databaseFilename;
		this.dataObjectInvocationHandlerFactory = dataObjectInvocationHandlerFactory;
		this.databaseHelperFactory = databaseHelperFactory;
	}

	public <T extends DBObject> T create(Class<T> type) throws Exception {
		DataObjectInvocationHandler<T> dataObjectInvocationHandler = dataObjectInvocationHandlerFactory
				.create(context, databaseFilename, type);
		dataObjectInvocationHandler.newInstance();
		return instanceByProxy(type, dataObjectInvocationHandler);
	}

	public <T extends DBObject> T getById(Class<T> type, long id) {
		DataObjectInvocationHandler<T> dataObjectInvocationHandler = dataObjectInvocationHandlerFactory
				.create(context, databaseFilename, type);
		T object = null;
		try {
			dataObjectInvocationHandler.loadById(id);
			object = instanceByProxy(type, dataObjectInvocationHandler);
		} catch (ObjectNotFound e) {
		}
		return object;
	}

	public <T extends DBObject> void deleteAll(Class<T> type) {
		DatabaseHelper<T> databaseHelper = databaseHelperFactory.create(context, databaseFilename,
				type);
		databaseHelper.deleteAll();
	}

	public <T extends DBObject> List<T> getAll(Class<T> type) {
		DatabaseHelper<T> databaseHelper = databaseHelperFactory.create(context, databaseFilename,
				type);
		List<Map<String, Object>> all = databaseHelper.getAll();
		List<T> allObjects = new ArrayList<T>();
		for (Map<String, Object> values : all) {
			DataObjectInvocationHandler<T> invocationHandler = dataObjectInvocationHandlerFactory
					.create(context, databaseFilename, type);
			invocationHandler.loadValues(values);
			T object = instanceByProxy(type, invocationHandler);
			allObjects.add(object);
		}
		return allObjects;
	}

	private <T> T instanceByProxy(Class<T> type, DataObjectInvocationHandler<T> invocationHandler) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type },
				invocationHandler));
	}
}
