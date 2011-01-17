package net.todd.sqliteorm;

import static org.junit.Assert.*;

import org.junit.Test;

public class InsertSqlFactoryTest {
	@Test
	public void createInsertStatementBasedOnFieldsInGivenMap() {
		String sql = InsertSqlFactory.createInsertSQL(Address.class);

		assertEquals(
				"insert into Address (city, houseNumber, state, streetName) values (?, ?, ?, ?)",
				sql);
	}
}
