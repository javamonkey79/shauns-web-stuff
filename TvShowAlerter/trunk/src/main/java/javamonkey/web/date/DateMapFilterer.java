package javamonkey.web.date;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class DateMapFilterer {

	public Multimap< String, Date > filterDateMapByDateAndSpan( Multimap< String, Date > inputMap, Date targetDate, final int weekSpan ) {
		return filterDateMap( inputMap, targetDate, new DatePredicate() {
			public boolean datesMatch( Date d1, Date d2 ) {
				int daysBetween = Days.daysBetween( LocalDate.fromDateFields( d1 ), LocalDate.fromDateFields( d2 ) ).getDays();
				return Math.abs( daysBetween ) <= ( weekSpan * 7 );
			}
		} );
	}

	public Multimap< String, Date > filterDateMapByDate( Multimap< String, Date > inputMap, Date targetDate ) {
		return filterDateMap( inputMap, targetDate, new DatePredicate() {
			public boolean datesMatch( Date d1, Date d2 ) {
				return LocalDate.fromDateFields( d1 ).equals( LocalDate.fromDateFields( d2 ) );
			}
		} );
	}

	private Multimap< String, Date > filterDateMap( Multimap< String, Date > inputMap, Date targetDate, DatePredicate predicate ) {
		Multimap< String, Date > returnMap = HashMultimap.create();

		Set< String > keySet = inputMap.keySet();
		for( String key : keySet ) {
			Collection< Date > dates = inputMap.get( key );
			for( Date showDate : dates ) {
				if ( predicate.datesMatch( showDate, targetDate ) ) {
					returnMap.put( key, showDate );
				}
			}
		}

		return returnMap;
	}

	private interface DatePredicate {
		boolean datesMatch( Date d1, Date d2 );
	}
}
