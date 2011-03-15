package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Combinator {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		new Combinator();
	}

	public Combinator() {
//		HashSet<String> set = new HashSet<String>(Arrays.asList("A",  "B",  "C"));
		HashSet< String > set = new HashSet< String >( Arrays.asList( "A", "A", "B" ) );
		ArrayList< HashSet< String >> list = new ArrayList< HashSet< String >>();
		for( int index = 0; index < set.size(); index++ ) {
			list.add( set );
		}

		ArrayList< ArrayList< String >> foo = new ArrayList< ArrayList< String >>();

		Set< Set< Object >> cartesianProduct = cartesianProduct( list.toArray( new Set[ list.size() ] ) );
		for( Set< Object > outer : cartesianProduct ) {
			ArrayList< String > bar = new ArrayList< String >();
			for( Object inner : outer ) {
				bar.add( String.valueOf( inner ) );
			}
			foo.add( bar );

			Collections.sort( bar );
		}

		Collections.sort( foo, new Comparator< ArrayList< String >>() {
			public int compare( ArrayList< String > o1, ArrayList< String > o2 ) {
				int compareTo = new Integer( o1.size() ).compareTo( new Integer( o2.size() ) );
				if ( compareTo == 0 ) {
					for( int index = 0; index < o1.size(); index++ ) {
						int itemCompareTo = o1.get( index ).compareTo( o2.get( index ) );
						if ( itemCompareTo != 0 ) {
							return itemCompareTo;
						}
					}
				}

				return compareTo;
			}
		} );
		System.out.println( foo );
	}

	public static Set< Set< Object >> cartesianProduct( Set< ? >... sets ) {
		if ( sets.length < 2 )
			throw new IllegalArgumentException( "Can't have a product of fewer than two sets (got " + sets.length + ")" );

		return _cartesianProduct( 0, sets );
	}

	private static Set< Set< Object >> _cartesianProduct( int index, Set< ? >... sets ) {
		Set< Set< Object >> ret = new HashSet< Set< Object >>();
		if ( index == sets.length ) {
			ret.add( new HashSet< Object >() );
		}
		else {
			for( Object obj : sets[ index ] ) {
				for( Set< Object > set : _cartesianProduct( index + 1, sets ) ) {
					set.add( obj );
					ret.add( set );
				}
			}
		}

		return ret;
	}
}
