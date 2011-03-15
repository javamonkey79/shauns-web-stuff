package javamonkey.web;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class SurveyXmlQuery {

	/**
	 * @param args
	 * @throws Exception
	 * @throws DocumentException
	 */
	public static void main( String[] args ) throws DocumentException, Exception {
		Document document = DocumentHelper.parseText( FileUtils.readFileToString( new File( "./region-scaner-surveys-bull-marsh.xml" ) ) );

		List< Node > nodes = document.selectNodes( "//e[count(descendant::e) > 3]" );
//		List< Node > nodes = document.selectNodes( "//e[text()='Feldspar']/../../.." );
		for( Node node : nodes ) {
//			System.out.println( node.asXML() );

			if ( node.asXML().contains( "Fire" ) ) {
				System.out.println( node.asXML() );
//				Element element = (Element)node;
//				if ( element.elements().size() > 3 ) {
//					System.out.println( element.getParent().getParent().asXML() );
//				}

			}
		}
	}

}
