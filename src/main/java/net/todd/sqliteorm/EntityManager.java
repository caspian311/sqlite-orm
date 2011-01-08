package net.todd.sqliteorm;

import java.lang.reflect.Proxy;

public class EntityManager {
	public <T extends DBObject> T create(Class<T> type) throws Exception {
		DatabaseWrapperInvocationHandler invocationHandler = new DatabaseWrapperInvocationHandler();
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] { type }, invocationHandler));
	}

	public <T extends DBObject> T getById(Class<T> type, String id) {
		DatabaseWrapperInvocationHandler invocationHandler = new DatabaseWrapperInvocationHandler();
		invocationHandler.retrieve(id);
		return instanceByProxy(type, invocationHandler);
	}

	private <T> T instanceByProxy(Class<T> type,
			DatabaseWrapperInvocationHandler invocationHandler) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(),
				new Class<?>[] { type }, invocationHandler));
	}
}
