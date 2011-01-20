package net.todd.sqliteorm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataObjectInvocationHandler<T> implements InvocationHandler {
	private static final String DELETE = "delete";
	private static final String SAVE = "save";
	private static final String SETTER_PREFIX = "set";
	private static final String GETTER_PREFIX = "get";
	private final Map<String, Object> storedValues = new HashMap<String, Object>();
	private final DatabaseHelper<T> databaseHelper;

	public DataObjectInvocationHandler(DatabaseHelper<T> databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object value = null;

		String methodName = method.getName();
		if (SAVE.equals(methodName)) {
			saveStoredValues();
		} else if (DELETE.equals(methodName)) {
			delete();
		} else if (methodName.startsWith(GETTER_PREFIX)) {
			String dataField = getDataFieldFromMethodName(GETTER_PREFIX, methodName);
			value = storedValues.get(dataField);
		} else if (methodName.startsWith(SETTER_PREFIX)) {
			String dataField = getDataFieldFromMethodName(SETTER_PREFIX, methodName);
			storedValues.put(dataField, args[0]);
		}

		return value;
	}

	private void delete() {
		databaseHelper.delete(Long.class.cast(storedValues.get("id")));
	}

	private String getDataFieldFromMethodName(String prefix, String methodName) {
		String name = methodName.substring(prefix.length());
		String firstLetter = name.substring(0, 1).toLowerCase();
		return firstLetter + name.substring(1);
	}

	public void newInstance() {
		long id = databaseHelper.createObject();
		storedValues.put("id", id);
	}

	private void saveStoredValues() {
		databaseHelper.updateObject(storedValues);
	}

	public void loadById(long id) throws ObjectNotFound {
		Map<String, Object> valuesFromDatabase = databaseHelper.loadObject(id);
		if (valuesFromDatabase.isEmpty()) {
			throw new ObjectNotFound();
		}
		loadValues(valuesFromDatabase);
	}

	public void loadValues(Map<String, Object> values) {
		storedValues.clear();
		storedValues.putAll(values);
	}
}
