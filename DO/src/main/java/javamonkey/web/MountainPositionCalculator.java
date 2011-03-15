package javamonkey.web;

public class MountainPositionCalculator {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		int mountain = 114;
		int tiles = 30;
		int x = mountain / tiles;
		System.out.println( mountain % tiles );
		System.out.println( x );
	}

}
