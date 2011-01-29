package javamonkey.web.link;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.htmlcleaner.HtmlCleaner;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings( "unchecked" )
public class LinkParser {

	private static Logger LOG = LoggerFactory.getLogger( LinkParser.class );

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( "MMMM dd, yyyy" );

	public String getLinkPageSource( String link ) throws Throwable {

		File file = new File( System.getProperty( "java.io.tmpdir" ), URLEncoder.encode( link + ".html", "UTF-8" ) );
		if ( !file.exists() || isFileOlderThanWeek( file ) ) {
			LOG.info( "File was not cached or is too old, fetching it." );

			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod( link );
			try {
				client.executeMethod( method );

				String responseString = new String( method.getResponseBody() );
				FileUtils.writeStringToFile( file, responseString );

				LOG.info( String.format( "Successfully wrote cache file: [%s]", file ) );

				return responseString;
			}
			finally {
				method.releaseConnection();
			}
		}
		else {
			LOG.info( String.format( "Returning cached link: [%s]", file ) );
			return FileUtils.readFileToString( file );
		}
	}

	public boolean isFileOlderThanWeek( File file ) {
		return Math.abs( Days.daysBetween( new LocalDateTime( file.lastModified() ), new LocalDateTime( new Date() ) ).getDays() ) >= 7;
	}

	public Document getLinkParsedPage( String pageSource ) throws Throwable {
		HtmlCleaner cleaner = new HtmlCleaner( pageSource );
		cleaner.clean();

		String xmlAsString = cleaner.getXmlAsString();
		String namespaceRemoved = xmlAsString.replaceAll( "xmlns=\"[^\"]+\"", "" );
		String extraWhitespaceRemoved = namespaceRemoved.replaceAll( "\\s+", " " );
		String lineBreaksRemoved = extraWhitespaceRemoved.replaceAll( "[\r\n]+", "" );

		return DocumentHelper.parseText( lineBreaksRemoved );
	}

	public List< Node > getTableNodesFromDocument( Document document ) {
		return document.selectNodes( "//th[starts-with(text(), \"Original\")]/../../.." );
	}

	public List< Date > getDatesFromTableNodes( List< Node > tableNodes ) throws Throwable {
		ArrayList< Date > dates = new ArrayList< Date >();

		if ( tableNodes.size() > 0 ) {
			for( Node table : tableNodes ) {
				List< Node > dateNodes = DocumentHelper.parseText( table.asXML() ).selectNodes( "//td/text()" );
				for( Node date : dateNodes ) {
					String dateText = date.getText().trim();
					if ( StringUtils.isNotBlank( dateText ) ) {
						try {
							dates.add( SIMPLE_DATE_FORMAT.parse( dateText ) );
						}
						catch ( Exception exception ) {
							// we don't really care if the format is whack...
							// System.out.println( exception.getMessage() );
						}
					}
				}
			}
		}

		return dates;
	}
}
