package com.test;

import java.util.Arrays;
import java.util.List;

public class LastItemsPrintTest {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		printList( Arrays.asList( "1" ) );
		printList( Arrays.asList( "1", "2" ) );
		printList( Arrays.asList( "1", "2", "3" ) );
		printList( Arrays.asList( "1", "2", "3", "4" ) );
	}

	public static void printList( List< String > items ) {
		for( int index = items.size() - 1; index >= 0 && items.size() - index <= 3; index-- ) {
//		for( int index = items.size() - 1, count = 0; index >= 0 && count < 3; index--, count++ ) {
			System.out.print( items.get( index ) + "," );
		}
		System.out.println();
	}
}
