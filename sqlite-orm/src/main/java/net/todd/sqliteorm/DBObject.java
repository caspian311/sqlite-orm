package net.todd.sqliteorm;

public interface DBObject {
	long getId();

	void save();

	void delete();
}
