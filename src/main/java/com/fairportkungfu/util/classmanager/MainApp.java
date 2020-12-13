package com.fairportkungfu.util.classmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.cli.ParseException;

public class MainApp {

	private static Logger LOG;
	private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	private static final String CMD_CREATE = "create";
	private static final String CMD_QUERY_POSTID = "querypost";
	private static final String CMD_QUERY_DATE = "querydate";

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

	public void execCreate(Date date) throws Exception {
		Calendar cal = null;

		cal = new Calendar.Builder().setInstant(date).build();

		// Note that month in Calendar class is zero based. Need to increment to get
		// real month.
		cm.createClasses(new Date(cal.getTimeInMillis()));
	}
	
	public void execQueryPostId(Long id) {
		System.out.println(cm.queryClassByPostId(id));
	}
	
	public void execQueryEventDate(Date date) {
		Calendar cal = null;

		cal = new Calendar.Builder().setInstant(date).build();
		
		System.out.println(cm.queryClassByDate(new Date(cal.getTimeInMillis())));
	}

	public static void main(String args[]) {
		MainApp app = null;
		String command = null;
		String commandArg = null;

		if (args.length < 2) {
			LOG.severe("Malformed args. Usage: MainApp command argument");
			System.exit(1);
		}

		command = args[0];
		commandArg = args[1];

		// If we got here we are good
		app = new MainApp();

		switch (command) {
		case CMD_CREATE:
			Date date = null;

			try {
				try {
					date = DATE_FORMATTER.parse(commandArg);
				} catch (java.text.ParseException e) {
					throw new ParseException(
							"Date not parseable. Must be in following format: '" + DATE_FORMAT_PATTERN + "'.");
				}
				if (date.before(new Date(System.currentTimeMillis()))) {
					throw new ParseException(
							"Date must be in future. Invalid date supplied: " + DATE_FORMATTER.format(date));
				}
				app.execCreate(date);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				e.printStackTrace();
			}
			break;
		case CMD_QUERY_POSTID:
			Long id;

			try {
				id = Long.parseLong(commandArg);
				app.execQueryPostId(id);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				e.printStackTrace();
			}
			break;
		case CMD_QUERY_DATE:
			Date queryDate = null;

			try {
				try {
					queryDate = DATE_FORMATTER.parse(commandArg);
				} catch (java.text.ParseException e) {
					throw new ParseException(
							"Date not parseable. Must be in following format: '" + DATE_FORMAT_PATTERN + "'.");
				}
				app.execQueryEventDate(queryDate);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				e.printStackTrace();
			}
			break;
		default:
			LOG.severe("Unknown command: " + command);
			break;
		}
	}

}
