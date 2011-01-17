package net.todd.sqliteorm;

//public class EntityManagerTest {
// @Mock
// private SQLiteOpenHelper openHelper;
//
// @Before
// public void setUp() throws Exception {
// MockitoAnnotations.initMocks(this);
// }
//
// private interface Person extends DBObject {
// String getName();
//
// String setName(String name);
//
// String getFavoriteColor();
//
// void setFavoriteColor(String color);
// }
//
// @Test
// public void canStoreAndRetrieveDataInTheObjectReturnedFromCreate() throws
// Exception {
// String name = UUID.randomUUID().toString();
//
// Person person = new EntityManager(openHelper).create(Person.class);
// person.setName(name);
//
// assertEquals(name, person.getName());
// }
//
// @Test
// public void
// canStoreAndRetrieveMultipleDataItemsInTheObjectReturnedFromCreate()
// throws Exception {
// String name = UUID.randomUUID().toString();
// String color = UUID.randomUUID().toString();
//
// Person person = new EntityManager(openHelper).create(Person.class);
// person.setName(name);
// person.setFavoriteColor(color);
//
// assertEquals(name, person.getName());
// assertEquals(color, person.getFavoriteColor());
// }
//
// @Test
// public void entitiesFromSameManagerHaveIndependantValues() throws
// Exception {
// EntityManager entityManager = new EntityManager(openHelper);
//
// String name1 = UUID.randomUUID().toString();
// String color1 = UUID.randomUUID().toString();
// Person person1 = entityManager.create(Person.class);
// person1.setName(name1);
// person1.setFavoriteColor(color1);
//
// String name2 = UUID.randomUUID().toString();
// String color2 = UUID.randomUUID().toString();
// Person person2 = entityManager.create(Person.class);
// person2.setName(name2);
// person2.setFavoriteColor(color2);
//
// assertEquals(name1, person1.getName());
// assertEquals(color1, person1.getFavoriteColor());
//
// assertEquals(name2, person2.getName());
// assertEquals(color2, person2.getFavoriteColor());
// }
//
// @Test
// public void idFieldisPopulatedWhenSavedWithANumberGreaterThanZero()
// throws Exception {
// EntityManager entityManager = new EntityManager(openHelper);
//
// SQLiteDatabase database = mock(SQLiteDatabase.class);
// doReturn(database).when(openHelper).getWritableDatabase();
// SQLiteStatement insertStatement = mock(SQLiteStatement.class);
// doReturn(insertStatement).when(database).compileStatement(anyString());
// long id = new Random().nextLong();
// doReturn(id).when(insertStatement).executeInsert();
//
// Person newPerson = entityManager.create(Person.class);
//
// ArgumentCaptor<String> sqlStatementCaptor =
// ArgumentCaptor.forClass(String.class);
// verify(database).compileStatement(sqlStatementCaptor.capture());
// String sqlStatement = sqlStatementCaptor.getValue();
//
// assertEquals(id, newPerson.getId());
// assertEquals("insert into Person () values ()", sqlStatement);
// }
//
// @Test
// public void idsHaveAUniqueValuePerEntity() throws Exception {
// EntityManager entityManager = new EntityManager(openHelper);
//
// SQLiteDatabase database = mock(SQLiteDatabase.class);
// doReturn(database).when(openHelper).getWritableDatabase();
// SQLiteStatement insertStatement = mock(SQLiteStatement.class);
// doReturn(insertStatement).when(database).compileStatement(anyString());
// doAnswer(new Answer<Long>() {
// @Override
// public Long answer(InvocationOnMock invocation) throws Throwable {
// return new Random().nextLong();
// }
// }).when(insertStatement).executeInsert();
//
// List<Long> ids = new ArrayList<Long>();
// for (int i = 0; i < 100; i++) {
// Person newPerson = entityManager.create(Person.class);
// long id = newPerson.getId();
// assertFalse(ids.contains(id));
// ids.add(id);
// }
// }
//
// @Test
// public void savingTheEntityExecutesCorrectSQL() throws Exception {
// EntityManager entityManager = new EntityManager(openHelper);
//
// SQLiteDatabase database = mock(SQLiteDatabase.class);
// doReturn(database).when(openHelper).getWritableDatabase();
// SQLiteStatement sqlStatement = mock(SQLiteStatement.class);
// doReturn(sqlStatement).when(database).compileStatement(anyString());
//
// Person person1 = entityManager.create(Person.class);
// String name = UUID.randomUUID().toString();
// String color = UUID.randomUUID().toString();
// person1.setName(name);
// person1.setFavoriteColor(color);
// person1.save();
//
// verify(sqlStatement).execute();
// ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
// verify(database, atLeastOnce()).compileStatement(sqlCaptor.capture());
// List<String> sql = sqlCaptor.getAllValues();
// assertEquals(2, sql.size());
// assertEquals("update Person set (favoriteColor=?, name=?) where id=?",
// sql.get(1));
// }
//
// @Test
// @Ignore
// public void retrievingAnEntityByIdPopulatesAllFields() throws Exception {
// EntityManager entityManager = new EntityManager(openHelper);
//
// long id = new Random().nextLong();
// String name = UUID.randomUUID().toString();
// String color = UUID.randomUUID().toString();
// Person person = entityManager.getById(Person.class, id);
//
// SQLiteDatabase database = mock(SQLiteDatabase.class);
// doReturn(database).when(openHelper).getWritableDatabase();
// Cursor cursor = mock(Cursor.class);
// doReturn(cursor).when(database).query("Person", null, "where id=?",
// new String[] { "" + id }, null, null, null);
// doReturn(true).when(cursor).moveToFirst();
//
// assertEquals(id, person.getId());
// assertEquals(name, person.getName());
// assertEquals(color, person.getFavoriteColor());
// }
// }