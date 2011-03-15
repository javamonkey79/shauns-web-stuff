package com.test;

import java.util.ArrayList;
import java.util.Arrays;

public class ListRemovalTest {
	
	public static ArrayList<String> _listA = new ArrayList<String>(Arrays.asList("This", "", "is", "", "a", "", "test."));
	public static ArrayList<String> _listB = new ArrayList<String>(Arrays.asList("", "Something", "", "Something", "", "Something"));
	public static ArrayList<String> _listC = new ArrayList<String>(Arrays.asList("A", "", "A", "", "", ""));
	public static ArrayList<String> _listD = new ArrayList<String>(Arrays.asList("", "what", "the", "fuck?", "", ""));
	public static ArrayList<String> _listE = new ArrayList<String>(Arrays.asList("", "", "", "", "", ""));

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printRemove(_listA);
		printRemove(_listB);
		printRemove(_listC);
		printRemove(_listD);
		printRemove(_listE);
	}
	
	public static void printRemove(ArrayList<String> list){
		int i = 0;
		while(i < list.size()){
			String item = list.get(i);
			if(item  == null || item.trim().length() == 0){
				list.remove(i);
			}else{
				i++;
			}
		}
		
		System.out.println(list);
	}

}
