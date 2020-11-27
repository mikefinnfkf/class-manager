package com.fairportkungfu.util.eventcreator;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

public class MainApp {

	private static Logger log = Logger.getLogger(MainApp.class.getName());


	public MainApp() {

		
		/*
		 * String[] tzs = TimeZone.getAvailableIDs();
		 * 
		 * for (String tz : tzs) { log.info("TZ: "+tz); }
		 */
		
		
		ClassManager cm = null;
		
		cm = new ClassManager();
		
		cm.createClasses(2020, 12, 1);
		cm.createClasses(2020, 12, 3);
		cm.createClasses(2020, 12, 5);
		cm.createClasses(2020, 12, 6);

	}
	


	public static void main(String args[]) {
		
	
		new MainApp();
	}

}
