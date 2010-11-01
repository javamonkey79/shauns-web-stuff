package org.workplacescheduler.dal;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.workplacescheduler.model.Employee;
import org.workplacescheduler.model.Job;
import org.workplacescheduler.model.Workweek;

/**
 * @author last modified by: $Author$
 * @version $Revision$ $Date$
 */
public class SchedulerServiceDalImplHolder {

	private final SchedulerServiceDal _schedulerServiceDalImpl = new SchedulerServiceDalXmlFileImpl();

	public SchedulerServiceDal getSchedulerServiceDalImpl() {
		return _schedulerServiceDalImpl;
	}

	private static final ModelSerializer MODEL_SERIALIZER = new ModelSerializer();

	private static final String USER_HOME_PROPERTY = System.getProperty( "user.home" );
	private static final File JOBS_FILE = new File( USER_HOME_PROPERTY, "jobs.xml" );
	private static final File EMPLOYEES_FILE = new File( USER_HOME_PROPERTY, "employees.xml" );

	/**
	 * The Class SchedulerServiceDalXmlFileImpl.
	 * 
	 * @author last modified by: $Author$
	 * @version $Revision$ $Date$
	 */
	private class SchedulerServiceDalXmlFileImpl implements SchedulerServiceDal {

		private File _workweekFile;

		private Workweek _workweek;
		private ArrayList< Job > _jobs;
		private ArrayList< Employee > _employees;

		/**
		 * {@inheritDoc}
		 */
		public Workweek getWorkweek( final String workweekIdentifier ) throws Exception {
			if ( _workweek == null ) {
				loadWorkweekFromFile( workweekIdentifier );
			}

			return _workweek;
		}

		/**
		 * {@inheritDoc}
		 */
		public ArrayList< Job > getAllJobs() throws Exception {
			if ( _jobs == null ) {
				loadJobsFromFile();
			}

			return _jobs;
		}

		/**
		 * {@inheritDoc}
		 */
		public ArrayList< Employee > getAllEmployees() throws Exception {
			if ( _employees == null ) {
				loadEmployeesFromFile();
			}

			return _employees;
		}

		private void loadWorkweekFromFile( final String workweekIdentifier ) throws Exception {
			String workweekXmlStringFromFile = getWorkweekXmlStringFromFile( workweekIdentifier );
			if ( StringUtils.isNotBlank( workweekXmlStringFromFile ) ) {
				_workweek = MODEL_SERIALIZER.deserializeWorkweek( workweekXmlStringFromFile );
				return;
			}

			_workweek = new Workweek();
		}

		private void loadJobsFromFile() throws Exception {
			String jobsXmlStringFromFile = getJobsXmlStringFromFile();
			if ( StringUtils.isNotBlank( jobsXmlStringFromFile ) ) {
				_jobs = MODEL_SERIALIZER.deserializeJobs( jobsXmlStringFromFile );
				return;
			}

			_jobs = new ArrayList< Job >();
		}

		private void loadEmployeesFromFile() throws Exception {
			String employeesXmlStringFromFile = getEmployeesXmlStringFromFile();
			if ( StringUtils.isNotBlank( employeesXmlStringFromFile ) ) {
				_employees = MODEL_SERIALIZER.deserializeEmployees( employeesXmlStringFromFile );
				return;
			}

			_employees = new ArrayList< Employee >();
		}

		private String getWorkweekXmlStringFromFile( final String workweekIdentifier ) throws Exception {
			_workweekFile = new File( workweekIdentifier );

			return getXmlFromFile( _workweekFile );
		}

		private String getJobsXmlStringFromFile() throws Exception {
			return getXmlFromFile( JOBS_FILE );
		}

		private String getEmployeesXmlStringFromFile() throws Exception {
			return getXmlFromFile( EMPLOYEES_FILE );
		}

		/**
		 * {@inheritDoc}
		 */
		public void saveWorkweek() throws Exception {
			saveWorkweekToFile();
		}

		/**
		 * {@inheritDoc}
		 */
		public void saveAllJobs() throws Exception {
			saveJobsToFile();
		}

		/**
		 * {@inheritDoc}
		 */
		public void saveAllEmployees() throws Exception {
			saveEmployeesToFile();
		}

		private void saveWorkweekToFile() throws Exception {
			saveWorkweekXmlStringToFile( MODEL_SERIALIZER.serializeWorkweek( _workweek ) );
		}

		private void saveJobsToFile() throws Exception {
			saveJobsXmlStringToFile( MODEL_SERIALIZER.serializeJobs( _jobs ) );
		}

		private void saveEmployeesToFile() throws Exception {
			saveEmployeesXmlStringToFile( MODEL_SERIALIZER.serializeEmployees( _employees ) );
		}

		private void saveWorkweekXmlStringToFile( final String serializedWorkweek ) throws Exception {
			saveXmlToFile( serializedWorkweek, _workweekFile );
		}

		private void saveJobsXmlStringToFile( final String serializedJobs ) throws Exception {
			saveXmlToFile( serializedJobs, JOBS_FILE );
		}

		private void saveEmployeesXmlStringToFile( final String serializedEmployees ) throws Exception {
			saveXmlToFile( serializedEmployees, EMPLOYEES_FILE );
		}

		private String getXmlFromFile( final File xmlFile ) throws Exception {
			try {
				if ( xmlFile != null && xmlFile.exists() ) {
					return FileUtils.readFileToString( xmlFile );
				}
				return null;
			}
			catch ( Exception exception ) {
				throw exception;
			}
		}

		private void saveXmlToFile( final String serializedXml, final File employeesFile ) throws Exception {
			try {
				FileUtils.writeStringToFile( employeesFile, serializedXml );
			}
			catch ( Exception exception ) {
				throw exception;
			}
		}
	}
}