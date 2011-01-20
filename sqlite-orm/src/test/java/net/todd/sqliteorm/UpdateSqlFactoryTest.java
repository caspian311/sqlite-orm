package net.todd.sqliteorm;

import static org.junit.Assert.*;

import org.junit.Test;

public class UpdateSqlFactoryTest {
	@Test
	public void createUpdateStatementBasedOnFieldsInGivenMap() {
		String sql = UpdateSqlFactory.createUpdateSQL(Address.class);

		assertEquals("update Address set city=?, houseNumber=?, state=?, streetName=? where id=?",
				sql);
	}
}
