package com.fairportkungfu.util.classmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainApp {

	private static Logger LOG;
	private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	private static final String CMD_CREATE = "create";
	
	static {
		String path = MainApp.class.getClassLoader().getResource("app.logging.properties").getFile();
		System.setProperty("java.util.logging.config.file", path);
		LOG = Logger.getLogger(MainApp.class.getName());
		DATE_FORMATTER.setLenient(false);
	}
	
	private ClassManager cm = null;
	
	public MainApp() {
		cm = new ClassManager();
	}
	
	public void execCreate(Date date) throws Exception
	{
		Calendar cal = null;
		
		cal = new Calendar.Builder().setInstant(date).build();
		
		// Note that month in Calendar class is zero based. Need to increment to get real month. 
		cm.createClasses(new Date(cal.getTimeInMillis()));
	}

	public static void main(String args[]) {
		MainApp app = null;
		CommandLineParser parser = null;
		Options options = new Options();
		CommandLine line = null;
		String dateString = null;
		String command = null;
		Date date = null;
		
		

		 

		parser = new DefaultParser();
		// options.addOption("c", "create-class", true, "create class");
		options.addOption(OptionBuilder.withArgName("date").hasArg().withDescription("date for which to modify classes ("+DATE_FORMAT_PATTERN+")")
				.isRequired(true).create("date"));

		options.addOption(OptionBuilder.withArgName("command").hasArg()
				.withDescription("command to execute (create is only supported option)").isRequired(true)
				.create("command"));

		try {
			line = parser.parse(options, args);

			if (line.hasOption("date")) {
				try {
					date = DATE_FORMATTER.parse(line.getOptionValue("date"));
				} catch (java.text.ParseException e) {
					throw new ParseException("Date not parseable. Must be in following format: '"+DATE_FORMAT_PATTERN+"'.");
				}
				if (date.before(new Date(System.currentTimeMillis()))) {
					throw new ParseException ("Date must be in future. Invalid date supplied: "+DATE_FORMATTER.format(date));
				}
			}
			if (line.hasOption("command")) {
				command = line.getOptionValue("command");
				
				// Validate command
				if (!"create".equals(command)) {
					throw new ParseException("Invalid command. Must be "+CMD_CREATE);
				}
			}

			LOG.info("Date: " + DATE_FORMATTER.format(date)+"; Command: "+command);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			LOG.severe(e.toString());
		}

		// If we got here we are good
		app = new MainApp();	
		
		switch (command) {
		case CMD_CREATE:
			try {
				app.execCreate(date);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				e.printStackTrace();
			}			
			break;
		default:
			LOG.severe("Unknown command: "+command);
			break;
		}
	}

}
