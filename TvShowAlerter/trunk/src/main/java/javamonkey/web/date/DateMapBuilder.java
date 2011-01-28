package javamonkey.web.date;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javamonkey.web.link.LinkFileHelper;
import javamonkey.web.link.LinkParser;

import org.dom4j.Document;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class DateMapBuilder {

	private static final LinkParser LINK_PARSER = new LinkParser();
	private static final LinkFileHelper LINK_FILE_HELPER = new LinkFileHelper();

	public Multimap< String, Date > buildDateMap() throws Throwable {
		Multimap< String, Date > dateShowMap = HashMultimap.create();

		Map< String, String > tvShowLinks = LINK_FILE_HELPER.getTvShowLinks();
		for( String linkName : tvShowLinks.keySet() ) {
			String pageSource = LINK_PARSER.getLinkPageSource( tvShowLinks.get( linkName ) );
			Document parsedPage = LINK_PARSER.getLinkParsedPage( pageSource );

			List< Date > latestDates = LINK_PARSER.getDatesFromTableNodes( LINK_PARSER.getTableNodesFromDocument( parsedPage ) );
			dateShowMap.putAll( linkName, latestDates );
		}

		return dateShowMap;
	}
}
