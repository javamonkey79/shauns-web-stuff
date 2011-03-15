package com.test;

import java.io.IOException;
import java.util.Properties;

public class TestPropertyLoad {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main( String[] args ) throws Throwable {
		Properties properties = new Properties();
		properties.load( ClassLoader.getSystemResourceAsStream( "foo.properties" ) );
		System.out.println( properties );

	}
}
