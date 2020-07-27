/**
 * 
 */
package com.weatherforecast.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtils {

	public static String getDriverPath(String key) {
		try {
			return TestProps.instance().readProp(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

class TestProps {
	private static TestProps testProps = null;
	private static Properties props = null;

	private TestProps() {

	}

	public static TestProps instance() throws IOException {

		if (testProps == null) {
			testProps = new TestProps();
			InputStream in = TestProps.class.getResourceAsStream("test.properties");
			props = new Properties();
			props.load(in);
		}

		return testProps;
	}
	
	public String readProp(String key){
		return (String) this.props.get(key);
	}

}