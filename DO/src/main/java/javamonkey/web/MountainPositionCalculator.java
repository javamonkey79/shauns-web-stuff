package javamonkey.web;

public class MountainPositionCalculator {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		int mountain =Integer.parseInt( args[0]);
		int tiles = 30;
		int x = mountain / tiles;
		System.out.println( mountain % tiles + ", " + x );
	}

}
