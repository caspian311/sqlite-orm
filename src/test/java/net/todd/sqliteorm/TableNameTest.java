package net.todd.sqliteorm;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableNameTest {
	@Test
	public void innerClassWorks() throws Exception {
		assertEquals("Monkey", TableName.getTableNameFromClass(Monkey.class));
	}

	@Test
	public void regularInterfaceWorks() throws Exception {
		assertEquals("RegularInterface", TableName.getTableNameFromClass(RegularInterface.class));
	}

	public class Monkey implements DBObject {
		@Override
		public long getId() {
			return 0;
		}

		@Override
		public void save() {
		}

		@Override
		public void delete() {
		}
	}
}
