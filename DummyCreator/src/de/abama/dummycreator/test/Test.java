package de.abama.dummycreator.test;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<String> testList = new ArrayList<String>();
		//testList.add("a");
		testList.add(1, "b");
		
		System.out.println(testList);
	}

}
