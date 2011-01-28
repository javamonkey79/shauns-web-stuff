package javamonkey.web.link;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.htmlcleaner.HtmlCleaner;

@SuppressWarnings( "unchecked" )
public class LinkParser {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( "MMMM dd, yyyy" );

	public String getLinkPageSource( String link ) throws Throwable {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( link );
		try {
			client.executeMethod( method );

			return new String( method.getResponseBody() );
		}
		finally {
			method.releaseConnection();
		}
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
