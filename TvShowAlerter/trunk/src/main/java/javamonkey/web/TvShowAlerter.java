package javamonkey.web;

import java.util.Date;

import javamonkey.web.date.DateMapBuilder;
import javamonkey.web.report.TvShowReportBuilder;

import com.google.common.collect.Multimap;

public class TvShowAlerter {

	private static final TvShowReportBuilder TV_SHOW_REPORT_BUILDER = new TvShowReportBuilder();
	private static final DateMapBuilder DATE_MAP_BUILDER = new DateMapBuilder();

	public static void main( String[] args ) throws Throwable {
		Multimap< String, Date > dateShowMap = DATE_MAP_BUILDER.buildDateMap();

		System.out.println( TV_SHOW_REPORT_BUILDER.getRecentShowsString( dateShowMap ) );
		System.out.println( TV_SHOW_REPORT_BUILDER.getShowsPlayingOnDateString( dateShowMap, new Date() ) );

		// construct email from map

		// send email
	}
}
