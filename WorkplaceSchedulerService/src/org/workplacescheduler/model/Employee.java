package org.workplacescheduler.model;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "Employee" )
public class Employee {

	@XStreamAlias( "EmployeeId" )
	private int _employeeId;

	@XStreamAlias( "FirstName" )
	private String _firstName = "";

	@XStreamAlias( "LastName" )
	private String _lastName = "";

	public Employee( final int employeeId, final String firstName, final String lastName ) {
		setEmployeeId( employeeId );
		setFirstName( firstName );
		setLastName( lastName );
	}

	public int getEmployeeId() {
		return _employeeId;
	}

	public void setEmployeeId( final int employeeId ) {
		if ( employeeId >= 0 ) {
			_employeeId = employeeId;
		}
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName( final String firstName ) {
		if ( StringUtils.isNotBlank( firstName ) ) {
			_firstName = firstName;
		}
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName( final String lastName ) {
		if ( StringUtils.isNotBlank( lastName ) ) {
			_lastName = lastName;
		}
	}

	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		fullName.append( getLastName() );

		if ( getLastName().length() > 0 ) {
			fullName.append( ", " );
		}
		fullName.append( getFirstName() );

		return fullName.toString();
	}

	@Override
	public String toString() {
		return getFullName() + "\n" + super.toString();
	}

}
