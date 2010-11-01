package org.workplacescheduler.dal;

import java.util.ArrayList;

import org.workplacescheduler.model.Employee;
import org.workplacescheduler.model.Job;
import org.workplacescheduler.model.Workweek;

public interface SchedulerServiceDal {

	Workweek getWorkweek( String workweekIdentifier ) throws Exception;

	ArrayList< Job > getAllJobs() throws Exception;

	ArrayList< Employee > getAllEmployees() throws Exception;

	void saveWorkweek() throws Exception;

	void saveAllJobs() throws Exception;

	void saveAllEmployees() throws Exception;

}