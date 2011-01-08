package net.todd.sqliteorm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class EntityManagerTest {
	private interface Person extends DBObject {
		String getName();

		String setName(String name);

		String getFavoriteColor();

		void setFavoriteColor(String color);
	}

	@Test
	public void canStoreAndRetrieveDataInTheObjectReturnedFromCreate()
			throws Exception {
		String name = UUID.randomUUID().toString();

		Person person = new EntityManager().create(Person.class);
		person.setName(name);

		assertEquals(name, person.getName());
	}

	@Test
	public void canStoreAndRetrieveMultipleDataItemsInTheObjectReturnedFromCreate()
			throws Exception {
		String name = UUID.randomUUID().toString();
		String color = UUID.randomUUID().toString();

		Person person = new EntityManager().create(Person.class);
		person.setName(name);
		person.setFavoriteColor(color);

		assertEquals(name, person.getName());
		assertEquals(color, person.getFavoriteColor());
	}

	@Test
	public void entitiesFromSameManagerHaveIndependantValues() throws Exception {
		EntityManager entityManager = new EntityManager();

		String name1 = UUID.randomUUID().toString();
		String color1 = UUID.randomUUID().toString();
		Person person1 = entityManager.create(Person.class);
		person1.setName(name1);
		person1.setFavoriteColor(color1);

		String name2 = UUID.randomUUID().toString();
		String color2 = UUID.randomUUID().toString();
		Person person2 = entityManager.create(Person.class);
		person2.setName(name2);
		person2.setFavoriteColor(color2);

		assertEquals(name1, person1.getName());
		assertEquals(color1, person1.getFavoriteColor());

		assertEquals(name2, person2.getName());
		assertEquals(color2, person2.getFavoriteColor());
	}

	@Test
	public void idIsAFieldThatIsProvidedAndHasAUniqueValuePerEntity()
			throws Exception {
		EntityManager entityManager = new EntityManager();

		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			String id = entityManager.create(Person.class).getId();
			assertFalse(ids.contains(id));
			ids.add(id);
		}
	}

	@Test
	public void afterSavingTheEntityYouCanComeBackToItLater() throws Exception {
		EntityManager entityManager = new EntityManager();

		Person person1 = entityManager.create(Person.class);
		String name = UUID.randomUUID().toString();
		String color = UUID.randomUUID().toString();
		person1.setName(name);
		person1.setFavoriteColor(color);
		person1.save();

		Person person2 = entityManager.getById(Person.class, person1.getId());

		assertEquals(name, person2.getName());
		assertEquals(color, person2.getFavoriteColor());
	}
}