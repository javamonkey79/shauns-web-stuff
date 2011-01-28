package javamonkey.web;

import java.io.File;
import java.util.Date;
import java.util.List;

import javamonkey.web.link.LinkParser;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.junit.Test;

import com.ibm.icu.util.GregorianCalendar;

public class LinkParserTest {

	private static final LinkParser LINK_PARSER = new LinkParser();

	@Test
	public void testGetPageSource() throws Throwable {
		System.out.println( LINK_PARSER.getLinkPageSource( "http://en.wikipedia.org/wiki/Simpsons_episodes" ) );
	}

	@Test
	public void testGetParsedTables() throws Throwable {
		Document parsedPage =
			LINK_PARSER.getLinkParsedPage( FileUtils.readFileToString( new File( LinkParserTest.class.getResource( "/pageSource.xml" ).toURI() ) ) );

		List< Node > tableNodesFromDocument = LINK_PARSER.getTableNodesFromDocument( parsedPage );
//		for( Node node : tableNodesFromDocument ) {
//			System.out.println( node.asXML() );
//		}

		Assert.assertEquals( 22, tableNodesFromDocument.size() );

		List< Date > datesFromTableNodes = LINK_PARSER.getDatesFromTableNodes( tableNodesFromDocument );

		Assert.assertEquals( new GregorianCalendar( 2011, 1, 13 ).getTime(), datesFromTableNodes.get( datesFromTableNodes.size() - 1 ) );
	}

}
