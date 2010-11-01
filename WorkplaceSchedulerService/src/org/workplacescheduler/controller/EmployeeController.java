package org.workplacescheduler.controller;

import org.workplacescheduler.dal.SchedulerServiceDal;
import org.workplacescheduler.model.Employee;

/**
 * @author last modified by: $Author$
 * @version $Revision$ $Date$
 */
public class EmployeeController extends SchedulerController {

	public EmployeeController( final SchedulerServiceDal schedulerServiceDal ) {
		super( schedulerServiceDal );
	}

	public Employee findEmployeeByLastName( final String lastNamePattern ) throws Exception {
		for( Employee employee : getSchedulerServiceDal().getAllEmployees() ) {
			if ( employee.getLastName().matches( lastNamePattern ) ) {
				return employee;
			}
		}

		return null;
	}

}
