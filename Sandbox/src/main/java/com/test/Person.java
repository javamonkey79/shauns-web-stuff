package com.test;

public class Person {

	private String _firstName;
	private String _lastName;

	public Person() {}

	public Person( String firstName, String lastName ) {
		_firstName = firstName;
		_lastName = lastName;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName( String firstName ) {
		_firstName = firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName( String lastName ) {
		_lastName = lastName;
	}

}
