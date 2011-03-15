package javamonkey.web;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javamonkey.web.link.LinkParser;

import org.dom4j.Document;
import org.dom4j.Node;

public class RegionScanner {

	private static final LinkParser LINK_PARSER = new LinkParser();

	/**
	 * @param args
	 * @throws Throwable
	 */
	@SuppressWarnings( "unchecked" )
	public static void main( String[] args ) throws Throwable {
		String link = "http://dungeonoverlord.station.sony.com/MyLairServer/DelegateServlet?act=loadRegionJSON&id=1";
		String cookieData =
			"JSESSIONID=DA38E8F040D236561ADF55A70F05EF08.lvstcs-t01-11009; locale=en_US; s_cc=true; s_sq=%5B%5BB%5D%5D; SESSION_SERVER_HASHED_REMEMBER_ME_COOKIE=NjgzMjk4MDA1OjEzMDEzMzk2MzQ3MzA6NzhiMDMwZDhiZDYwNjg1MTU2ODAwMDRmMzI1ZmI5Zjk=; fbs_110362692359888=\"access_token=110362692359888%7C2.HWHXCpYMIbyNMxt3Lb9e9A__.3600.1300150800-507492112%7CTe0ZKKE0-r8OgqLmKX3wAHVwhBA&base_domain=station.sony.com&expires=1300150800&secret=TDAmnVS2zzbiRMqKo_oWgg__&session_key=2.HWHXCpYMIbyNMxt3Lb9e9A__.3600.1300150800-507492112&sig=6655ca5d8af9c575bd791a5714c7e6e0&uid=507492112\"";
		String linkPageSource = LINK_PARSER.getLinkPageSource( link, cookieData, true );
		System.out.println( linkPageSource );

		List< Node > mountainSurveys = new ArrayList< Node >();

		int i = 0;

		Document jsonXml = LINK_PARSER.convertJsonToXml( linkPageSource );
		List< Node > nodes = jsonXml.selectNodes( "//id" );
		for( Node node : nodes ) {
//			link = "http://dungeonoverlord.station.sony.com/MyLairServer/DelegateServlet?act=loadMountainJSON&id=" + 122212;
			link = "http://dungeonoverlord.station.sony.com/MyLairServer/DelegateServlet?act=loadMountainJSON&id=" + node.getText();
			linkPageSource = LINK_PARSER.getLinkPageSource( link, cookieData, true );

			Document mountain = LINK_PARSER.convertJsonToXml( linkPageSource );
			List< Node > surveyedMountains = mountain.selectNodes( "//survey/../../.." );
			for( Node mountainNode : surveyedMountains ) {
				System.out.println( "added:\n" + mountainNode.asXML() );
			}

			mountainSurveys.addAll( surveyedMountains );

			System.out.println( "on " + i++ + " of: " + nodes.size() );

			//Thread.sleep( 5000 );
//			if ( i > 200 ) {
//				Thread.sleep( 2000 );
//			}
		}

		FileWriter fstream = new FileWriter( "region-scaner-surveys.xml", true );
		fstream.write( "<surveys>" );

		for( Node node : mountainSurveys ) {
			fstream.write( node.asXML() + "\n" );
		}
		fstream.write( "</surveys>" );
		fstream.close();

	}

}
