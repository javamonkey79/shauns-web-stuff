package javamonkey.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CookieTest {
	public static void main( final String[] args ) throws MalformedURLException, IOException {
		URLConnection connection =
			new URL( "http://dungeonoverlord.station.sony.com/MyLairServer/DelegateServlet?act=loadRegionJSON&id=1" ).openConnection();
		List< String > cookies = connection.getHeaderFields().get( "Set-Cookie" );
		for( String cookie : cookies ) {
			System.out.println( cookie );
		}
	}
}
