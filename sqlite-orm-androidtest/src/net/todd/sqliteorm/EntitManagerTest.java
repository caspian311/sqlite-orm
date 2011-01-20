package net.todd.sqliteorm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;

public class EntitManagerTest extends ActivityInstrumentationTestCase2<TestActivity> {
	private final String DB_FILE = "test.db";

	public EntitManagerTest() {
		super("net.todd.sqliteorm", TestActivity.class);
	}

	private interface Person extends DBObject {
		String getName();

		String setName(String name);

		String getFavoriteColor();

		void setFavoriteColor(String color);
	}

	public void testCanStoreAndRetrieveDataInTheObjectReturnedFromCreate() throws Exception {
		String name = UUID.randomUUID().toString();

		Person person = new EntityManager(getActivity(), DB_FILE).create(Person.class);
		person.setName(name);

		assertEquals(name, person.getName());
	}

	public void testCanStoreAndRetrieveMultipleDataItemsInTheObjectReturnedFromCreate()
			throws Exception {
		String name = UUID.randomUUID().toString();
		String color = UUID.randomUUID().toString();

		Person person = new EntityManager(getActivity(), DB_FILE).create(Person.class);
		person.setName(name);
		person.setFavoriteColor(color);

		assertEquals(name, person.getName());
		assertEquals(color, person.getFavoriteColor());
	}

	public void testEntitiesFromSameManagerHaveIndependantValues() throws Exception {
		EntityManager entityManager = new EntityManager(getActivity(), DB_FILE);

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

	public void testIdFieldisPopulatedWhenSavedWithANumberGreaterThanZero() throws Exception {
		EntityManager entityManager = new EntityManager(getActivity(), DB_FILE);

		Person newPerson = entityManager.create(Person.class);
		assertNotNull(newPerson.getId());
	}

	public void testIdsHaveAUniqueValuePerEntity() throws Exception {
		EntityManager entityManager = new EntityManager(getActivity(), DB_FILE);

		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < 100; i++) {
			Person newPerson = entityManager.create(Person.class);
			long id = newPerson.getId();
			assertFalse(ids.contains(id));
			ids.add(id);
		}
	}

	public void testSavingTheEntityExecutesCorrectSQL() throws Exception {
		Person person1 = new EntityManager(getActivity(), DB_FILE).create(Person.class);
		String name = UUID.randomUUID().toString();
		String color = UUID.randomUUID().toString();
		person1.setName(name);
		person1.setFavoriteColor(color);
		person1.save();

		long id = person1.getId();

		Person person2 = new EntityManager(getActivity(), DB_FILE).getById(Person.class, id);
		assertEquals(id, person2.getId());
		assertEquals(name, person2.getName());
		assertEquals(color, person2.getFavoriteColor());
	}

	public void testDeletingAllAndRetrievingAll() throws Exception {
		new EntityManager(getActivity(), DB_FILE).deleteAll(Person.class);
		List<Person> allPeople = new EntityManager(getActivity(), DB_FILE).getAll(Person.class);

		assertEquals(0, allPeople.size());

		new EntityManager(getActivity(), DB_FILE).create(Person.class);
		allPeople = new EntityManager(getActivity(), DB_FILE).getAll(Person.class);

		assertEquals(1, allPeople.size());

		new EntityManager(getActivity(), DB_FILE).create(Person.class);
		allPeople = new EntityManager(getActivity(), DB_FILE).getAll(Person.class);

		assertEquals(2, allPeople.size());

		new EntityManager(getActivity(), DB_FILE).create(Person.class);
		allPeople = new EntityManager(getActivity(), DB_FILE).getAll(Person.class);

		assertEquals(3, allPeople.size());

		new EntityManager(getActivity(), DB_FILE).deleteAll(Person.class);
		allPeople = new EntityManager(getActivity(), DB_FILE).getAll(Person.class);

		assertEquals(0, allPeople.size());
	}
}
