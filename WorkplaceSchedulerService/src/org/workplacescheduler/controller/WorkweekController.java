package org.workplacescheduler.controller;

import java.util.ArrayList;

import org.workplacescheduler.dal.SchedulerServiceDal;
import org.workplacescheduler.model.Employee;
import org.workplacescheduler.model.Shift;

/**
 * @author last modified by: $Author$
 * @version $Revision$ $Date$
 */
public class WorkweekController extends SchedulerController {

	public WorkweekController( final SchedulerServiceDal schedulerServiceDal ) {
		super( schedulerServiceDal );
	}

	public ArrayList< Shift > getShiftsForEmployee( final Employee employee ) throws Exception {
		ArrayList< Shift > returnShifts = new ArrayList< Shift >();

		for( Shift shift : getSchedulerServiceDal().getWorkweek(null).getShifts() ) {
			if ( shift.getEmployee().getEmployeeId() == employee.getEmployeeId() ) {
				returnShifts.add( shift );
			}
		}

		return returnShifts;
	}
}
