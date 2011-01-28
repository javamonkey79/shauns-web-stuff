package javamonkey.web;

import java.util.Date;
import java.util.GregorianCalendar;

import javamonkey.web.date.DateMapFilterer;
import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class DateMapFiltererTest {

	@Test
	public void testFilterDateMapByDateAndSpan() throws Throwable {

		Multimap< String, Date > inputMap = HashMultimap.create();
		inputMap.put( "testOutOfRange", new GregorianCalendar( 1970, 2, 15 ).getTime() );

		inputMap.put( "test1", new GregorianCalendar( 1980, 1, 23 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 1, 30 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 1 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 8 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 15 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 22 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 29 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 3, 3 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 3, 10 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 4, 21 ).getTime() );

		int weekSpan = 2;

		Multimap< String, Date > observed =
			new DateMapFilterer().filterDateMapByDateAndSpan( inputMap, new GregorianCalendar( 1980, 2, 15 ).getTime(), weekSpan );

		Multimap< String, Date > expected = HashMultimap.create();
		expected.put( "test1", new GregorianCalendar( 1980, 2, 1 ).getTime() );
		expected.put( "test1", new GregorianCalendar( 1980, 2, 8 ).getTime() );
		expected.put( "test1", new GregorianCalendar( 1980, 2, 15 ).getTime() );
		expected.put( "test1", new GregorianCalendar( 1980, 2, 22 ).getTime() );
		expected.put( "test1", new GregorianCalendar( 1980, 2, 29 ).getTime() );

		System.out.println( expected );
		System.out.println( observed );

		Assert.assertEquals( expected, observed );
	}

	@Test
	public void filterDateMapByDate() throws Throwable {

		Multimap< String, Date > inputMap = HashMultimap.create();
		inputMap.put( "testOutOfRange", new GregorianCalendar( 1970, 2, 15 ).getTime() );

		inputMap.put( "test1", new GregorianCalendar( 1980, 1, 23 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 1, 30 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 1 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 8 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 15 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 22 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 2, 29 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 3, 3 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 3, 10 ).getTime() );
		inputMap.put( "test1", new GregorianCalendar( 1980, 4, 21 ).getTime() );

		Multimap< String, Date > observed = new DateMapFilterer().filterDateMapByDate( inputMap, new GregorianCalendar( 1980, 2, 15 ).getTime() );

		Multimap< String, Date > expected = HashMultimap.create();
		expected.put( "test1", new GregorianCalendar( 1980, 2, 15 ).getTime() );

		System.out.println( expected );
		System.out.println( observed );

		Assert.assertEquals( expected, observed );
	}
}
