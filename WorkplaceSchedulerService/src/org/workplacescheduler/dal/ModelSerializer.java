package org.workplacescheduler.dal;

import java.util.ArrayList;

import org.workplacescheduler.model.Employee;
import org.workplacescheduler.model.Job;
import org.workplacescheduler.model.Shift;
import org.workplacescheduler.model.Workweek;

import com.thoughtworks.xstream.XStream;

public class ModelSerializer {

	private final XStream _xStream;

	public ModelSerializer() {
		_xStream = new XStream();
		_xStream.processAnnotations( Employee.class );
		_xStream.processAnnotations( Job.class );
		_xStream.processAnnotations( Shift.class );
		_xStream.processAnnotations( Workweek.class );
	}

	public String serializeEmployees( final ArrayList< Employee > employees ) {
		return _xStream.toXML( employees );
	}

	@SuppressWarnings( "unchecked" )
	public ArrayList< Employee > deserializeEmployees( final String employeesXml ) {
		return (ArrayList< Employee >)_xStream.fromXML( employeesXml );
	}

	public String serializeJobs( final ArrayList< Job > jobs ) {
		return _xStream.toXML( jobs );
	}

	@SuppressWarnings( "unchecked" )
	public ArrayList< Job > deserializeJobs( final String jobsXml ) {
		return (ArrayList< Job >)_xStream.fromXML( jobsXml );
	}

	public String serializeWorkweek( final Workweek workweek ) {
		return _xStream.toXML( workweek );
	}

	public Workweek deserializeWorkweek( final String workweekXml ) {
		return (Workweek)_xStream.fromXML( workweekXml );
	}

}
