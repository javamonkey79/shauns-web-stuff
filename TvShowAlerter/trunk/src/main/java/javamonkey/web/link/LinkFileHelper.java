package javamonkey.web.link;

import java.util.Map;
import java.util.Properties;

public class LinkFileHelper {

//	public List< String > getTvShowLinks() throws Throwable {
//		ArrayList< String > links = new ArrayList< String >();
//
//		Scanner scanner = new Scanner( new File( LinkFileHelper.class.getResource( "/links.txt" ).toURI() ) );
//		while ( scanner.hasNext() ) {
//			links.add( scanner.nextLine() );
//		}
//
//		return links;
//	}

	@SuppressWarnings( {
		"rawtypes",
		"unchecked" } )
	public Map< String, String > getTvShowLinks() throws Throwable {
		Properties properties = new Properties();
		properties.load( LinkFileHelper.class.getResourceAsStream( "/links.properties" ) );

		return (Map)properties;
	}
}
