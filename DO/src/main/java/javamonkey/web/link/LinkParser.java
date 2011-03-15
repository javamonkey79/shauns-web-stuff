package javamonkey.web.link;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkParser {

	private static Logger LOG = LoggerFactory.getLogger( LinkParser.class );

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( "MMMM dd, yyyy" );

	public String getLinkPageSource( String link, String cookieData, boolean useCache ) throws Throwable {

		if ( useCache ) {
			File file = new File( System.getProperty( "java.io.tmpdir" ), URLEncoder.encode( link + ".html", "UTF-8" ) );
			if ( !file.exists() || isFileOlderThanWeek( file ) ) {
				LOG.info( "File was not cached or is too old, fetching it." );

				String responseString = "";

				responseString = getLinkPageSource( link, cookieData );

				FileUtils.writeStringToFile( file, responseString );
				LOG.info( String.format( "Successfully wrote cache file: [%s]", file ) );

				return responseString;
			}
			else {
				LOG.info( String.format( "Returning cached link: [%s]", file ) );
				return FileUtils.readFileToString( file );
			}
		}
		else {
			return getLinkPageSource( link, cookieData );
		}
	}

	public String getLinkPageSource( String link, String cookieData ) throws IOException, HttpException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( link );
		try {
			method.setRequestHeader( "Cookie", cookieData );
			client.executeMethod( method );

			return new String( method.getResponseBody() );
		}
		finally {
			method.releaseConnection();
		}
	}

	public Document convertJsonToXml( String inputJson ) throws Exception {
		XMLSerializer serializer = new XMLSerializer();
		JSON json = JSONSerializer.toJSON( inputJson );
		String jsonXml = serializer.write( json );

//		StringWriter out = new StringWriter();
//		OutputFormat format = OutputFormat.createPrettyPrint();
//		XMLWriter writer = new XMLWriter( out, format );
//		writer.write( DocumentHelper.parseText( jsonXml ) );
//		writer.close();

		return DocumentHelper.parseText( jsonXml );
	}

	private boolean isFileOlderThanWeek( File file ) {
		return Math.abs( Days.daysBetween( new LocalDateTime( file.lastModified() ), new LocalDateTime( new Date() ) ).getDays() ) >= 7;
	}

	public static void main( String[] args ) {
		System.out.println( new Date( 1297462787062L ) );
	}
}
