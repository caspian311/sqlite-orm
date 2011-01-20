package net.todd.sqliteorm;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreateTableSqlFactoryTest {
	@Test
	public void createTableStatementIsCorrectlyFormatted() {
		String sql = CreateTableSqlFactory.createTableSql(Address.class);

		assertEquals("create table Address (city TEXT, houseNumber INTEGER, "
				+ "state TEXT, streetName TEXT, id INTEGER PRIMARY KEY AUTOINCREMENT)", sql);
	}
}
