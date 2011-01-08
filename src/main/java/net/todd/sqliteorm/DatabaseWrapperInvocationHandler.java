package net.todd.sqliteorm;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseWrapperInvocationHandler implements InvocationHandler {
	private final Map<String, Object> storedValues = new HashMap<String, Object>();

	public DatabaseWrapperInvocationHandler() {
		storedValues.put("id", UUID.randomUUID().toString());
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object value = null;

		String methodName = method.getName();
		if ("save".equals(methodName)) {
			saveStoredValues();
		} else if (methodName.startsWith("get")) {
			String dataField = getDataFieldFromMethodName("get", methodName);
			value = storedValues.get(dataField);
		} else if (methodName.startsWith("set")) {
			String dataField = getDataFieldFromMethodName("set", methodName);
			storedValues.put(dataField, args[0]);
		}

		return value;
	}

	private String getDataFieldFromMethodName(String prefix, String methodName) {
		String name = methodName.substring(prefix.length());
		String firstLetter = name.substring(0, 1).toLowerCase();
		return firstLetter + name.substring(1);
	}

	private void saveStoredValues() {
		try {
			new Persistor(new File("/tmp/sqlite-orm.txt")).write(storedValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void retrieve(String id) {
		try {
			Map<?, ?> previousValues = (Map<?, ?>) new Persistor(new File(
					"/tmp/sqlite-orm.txt")).read();
			storedValues
					.putAll((Map<? extends String, ? extends Object>) previousValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
