package javamonkey.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javamonkey.web.date.DateMapBuilder;
import javamonkey.web.email.EmailSender;
import javamonkey.web.report.TvShowReportBuilder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;

public class TvShowAlerter {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( "MM/dd/yy" );
	private static Logger LOG = LoggerFactory.getLogger( TvShowAlerter.class );

	private static final EmailSender EMAIL_SENDER = new EmailSender();
	private static final TvShowReportBuilder TV_SHOW_REPORT_BUILDER = new TvShowReportBuilder();
	private static final DateMapBuilder DATE_MAP_BUILDER = new DateMapBuilder();

	public static void main( String[] args ) throws Throwable {
		Multimap< String, Date > dateShowMap = DATE_MAP_BUILDER.buildDateMap();

		//implement some fancy options to handle this
		//System.out.println( TV_SHOW_REPORT_BUILDER.getRecentShowsString( dateShowMap ) );

		Date showDateToCheck = getShowDateToCheck( args );

		String showsPlayingOnDateString = TV_SHOW_REPORT_BUILDER.getShowsPlayingOnDateString( dateShowMap, showDateToCheck );
		performEmailProcess( args, showsPlayingOnDateString );

	}

	private static void performEmailProcess( String[] args, String showsPlayingOnDateString ) throws Throwable {
		if ( StringUtils.isNotBlank( showsPlayingOnDateString ) ) {
			LOG.info( String.format( "Preparing to send email with body:\n%s", showsPlayingOnDateString ) );

			if ( args == null || args.length == 0 ) {
				LOG.warn( "No email password detected, will attempt to get it from email.properties file." );
				EMAIL_SENDER.sendEmail( "New TV Show(s) Today", showsPlayingOnDateString );
			}
			else {
				EMAIL_SENDER.sendEmail( "New TV Show(s) Today", showsPlayingOnDateString, args[ 0 ] );
			}
		}
		else {
			LOG.info( "No shows found for today." );
		}
	}

	private static Date getShowDateToCheck( String[] args ) {
		Date showDateToCheck = new Date();
		if ( args != null && args.length >= 2 ) {
			try {
				String date = args[ 1 ];

				LOG.info( String.format( "Trying to parse date: [%s]", date ) );

				showDateToCheck = SIMPLE_DATE_FORMAT.parse( date );

				LOG.info( String.format( "Using parsed date: [%s]", date ) );
			}
			catch ( Throwable throwable ) {
				LOG.error( throwable.getMessage() );
				LOG.info( "Exception caught, defaulting to today." );
			}
		}
		else {
			LOG.info( "Date arg not passed in {MM/dd/yy}, defaulting to today." );
		}
		return showDateToCheck;
	}
}
