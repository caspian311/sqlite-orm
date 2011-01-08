package net.todd.sqliteorm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;

public class Persistor {
	private final File file;

	public Persistor(File file) {
		this.file = file;
	}

	public void write(Object o) throws Exception {
		FileOutputStream output = new FileOutputStream(file);
		new XStream().toXML(o, output);
		output.close();
	}

	public Object read() throws Exception {
		FileInputStream input = new FileInputStream(file);
		Object object = new XStream().fromXML(input);
		input.close();

		return object;
	}
}
