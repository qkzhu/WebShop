package com.webshop.test;

import com.webshop.branch.china.DemoPlayerChina;
import com.webshop.branch.singapore.DemoPlayerSingapore;


public class MainOperation {

	public static void main(String[] args) {
		
		DemoPlayerChina demoFromChina = new DemoPlayerChina();
		demoFromChina.play();
		
		DemoPlayerSingapore demoFromSingapore = new DemoPlayerSingapore();
		demoFromSingapore.play();
	}
	
} // end of class MainOperation