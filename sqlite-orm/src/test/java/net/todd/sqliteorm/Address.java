package net.todd.sqliteorm;

public interface Address extends DBObject {
	void setHouseNumber(int houseNumber);

	int getHouseNumber();

	void setStreetName(String streetName);

	String getStreetName();

	void setCity(String city);

	String getCity();

	void setState(String state);

	String getState();
}
