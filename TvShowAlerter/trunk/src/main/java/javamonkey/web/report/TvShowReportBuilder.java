package javamonkey.web.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javamonkey.web.date.DateMapFilterer;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Multimap;

public class TvShowReportBuilder {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( "MM/dd/yy" );
	private static final DateMapFilterer DATE_MAP_FILTERER = new DateMapFilterer();

	public String getRecentShowsString( Multimap< String, Date > dateShowMap ) {
		StringBuilder recentShowsString = new StringBuilder( "===== Recent Shows =====\n" );
		Multimap< String, Date > filteredDateMap = DATE_MAP_FILTERER.filterDateMapByDateAndSpan( dateShowMap, new Date(), 2 );
		appendFilteredDatesToStringBuilder( recentShowsString, filteredDateMap, true );

		return recentShowsString.toString();
	}

	public String getShowsPlayingOnDateString( Multimap< String, Date > dateShowMap, Date date ) {
		String header = String.format( "===== Shows Playing on: %s =====\n", SIMPLE_DATE_FORMAT.format( date ) );
		StringBuilder showsPlayingOnDateString = new StringBuilder();
		Multimap< String, Date > filteredDateMap = DATE_MAP_FILTERER.filterDateMapByDate( dateShowMap, date );
		appendFilteredDatesToStringBuilder( showsPlayingOnDateString, filteredDateMap, false );

		if ( StringUtils.isNotBlank( showsPlayingOnDateString.toString() ) ) {
			showsPlayingOnDateString.insert( 0, header );
		}

		return showsPlayingOnDateString.toString();
	}

	public void appendFilteredDatesToStringBuilder( StringBuilder stringBuilder, Multimap< String, Date > filteredDateMap, boolean appendDates ) {
		for( String key : filteredDateMap.keySet() ) {

			stringBuilder.append( key );
			stringBuilder.append( "\n" );

			if ( appendDates ) {
				ArrayList< Date > interestedDates = new ArrayList< Date >( filteredDateMap.get( key ) );
				Collections.sort( interestedDates );

				for( Date showDate : interestedDates ) {
					stringBuilder.append( SIMPLE_DATE_FORMAT.format( showDate ) );
					stringBuilder.append( "\n" );
				}
			}
		}
	}
}
