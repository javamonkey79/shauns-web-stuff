package org.workplacescheduler.dal;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.workplacescheduler.model.Employee;
import org.workplacescheduler.model.Job;
import org.workplacescheduler.model.Shift;
import org.workplacescheduler.model.Workweek;

public class ModelSerializerTest {
	private static final ModelSerializer MODEL_SERIALIZER = new ModelSerializer();

	@Test
	public void testSerializeEmployees() {
		Employee testEmployee = new Employee( 0, "Billy", "Bob" );

		String serializedEmployee = MODEL_SERIALIZER.serializeEmployees( new ArrayList< Employee >( Arrays.asList( testEmployee ) ) );
		System.out.println( serializedEmployee );

		Employee deserializedEmployee = MODEL_SERIALIZER.deserializeEmployees( serializedEmployee ).get( 0 );

		assertEquals( deserializedEmployee.getLastName(), testEmployee.getLastName() );
	}

	@Test
	public void testSerializeJobs() {
		Job testJob = new Job( 0, "cook" );

		String serializedJob = MODEL_SERIALIZER.serializeJobs( new ArrayList< Job >( Arrays.asList( testJob ) ) );
		System.out.println( serializedJob );

		Job deserializedJob = MODEL_SERIALIZER.deserializeJobs( serializedJob ).get( 0 );

		assertEquals( deserializedJob.getJobName(), testJob.getJobName() );
	}

	@Test
	public void testSerializeWorkweek() {
		Shift testShift1 = new Shift( 0, new Employee( 0, "Billy", "Bob" ), new Job( 0, "cook" ), new Date(), new Date() );
		Shift testShift2 = new Shift( 0, new Employee( 1, "Carla", "May" ), new Job( 1, "dishwasher" ), new Date(), new Date() );

		Workweek testWorkweek = new Workweek( 0, new Date(), new ArrayList< Shift >( Arrays.asList( testShift1, testShift2 ) ) );
		String serialzedWorkweek = MODEL_SERIALIZER.serializeWorkweek( testWorkweek );
		System.out.println( serialzedWorkweek );

		Workweek deserializedWorkweek = MODEL_SERIALIZER.deserializeWorkweek( serialzedWorkweek );

		assertEquals( deserializedWorkweek.getShifts().get( 0 ).getJob().getJobName(), testWorkweek.getShifts().get( 0 ).getJob().getJobName() );
	}

}
