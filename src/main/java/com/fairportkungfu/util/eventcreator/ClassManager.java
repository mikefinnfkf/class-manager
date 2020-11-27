package com.fairportkungfu.util.eventcreator;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.fairportkungfu.util.eventcreator.dao.WpDbDao;
import com.fairportkungfu.util.eventcreator.model.FkfClass;
import com.fairportkungfu.util.eventcreator.model.WpEmEvents;
import com.fairportkungfu.util.eventcreator.model.WpEmTickets;
import com.fairportkungfu.util.eventcreator.model.WpPostMeta;
import com.fairportkungfu.util.eventcreator.model.WpPosts;

public class ClassManager {

	private static DateFormat dfDateOnly = new SimpleDateFormat("MM/dd/yyyy");
	private static DateFormat dfDateOnlyDashes = new SimpleDateFormat("MM-dd-yyyy");
	private static DateFormat dfWeekday = new SimpleDateFormat("EEE");
	private static DateFormat dfTime = new SimpleDateFormat("hh:mma");
	private static DateFormat dfTimeCompressed = new SimpleDateFormat("hhmma");
	private static DateFormat dfFullDateTimeMeta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat dfDateOnlyMeta = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat dfTimeOnlyMeta = new SimpleDateFormat("HH:mm:ss");
	private static final String TIME_ZONE_ID = "EST5EDT";
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone(TIME_ZONE_ID);
	private static final Long RSVP_WINDOW = 60L * 60L * 1000L * 2L; // 2 hours
	private static final Long EVENT_OWNER = 4L;
	private static final Integer ALLOWED_SPACES = 8;
	private static final String PROG_TIGERS = "Tigers";
	private static final String PROG_KIDS = "Kids";
	private static final String PROG_ADULTS = "Adults";
	private static final String PROG_TEST = "TEST_DONOTUSE";
	// Looks weird, but we don't want the classes to show up for registration until enabled. Just restore in the console.
	private static final String POST_STATUS = "publish";

	private Logger log = Logger.getLogger(getClass().getName());

	public ClassManager() {

	}

	public void createClasses(int year, int month, int day) {
		Calendar date = null;
		int dayOfWeek;

		log.info("year="+year+";month="+month+";day="+day);
		
		date = new Calendar.Builder().setDate(year, month-1, day).build();
		log.info(date.toString());
		// What day of week is it?
		dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			createClass(year, month, day, 10, 0, 45, PROG_ADULTS);
			break;
		case Calendar.TUESDAY:
			createClass(year, month, day, 17, 0, 20, PROG_TIGERS);
			createClass(year, month, day, 17, 35, 35, PROG_KIDS);
			createClass(year, month, day, 18, 25, 35, PROG_KIDS);
			createClass(year, month, day, 19, 15, 60, PROG_ADULTS);
			break;
		case Calendar.THURSDAY:
			createClass(year, month, day, 17, 0, 20, PROG_TIGERS);
			createClass(year, month, day, 17, 35, 35, PROG_KIDS);
			createClass(year, month, day, 18, 25, 35, PROG_KIDS);
			createClass(year, month, day, 19, 15, 60, PROG_ADULTS);
			break;
		case Calendar.SATURDAY:
			createClass(year, month, day, 9, 0, 20, PROG_TIGERS);
			createClass(year, month, day, 9, 35, 35, PROG_KIDS);
			createClass(year, month, day, 10, 25, 35, PROG_KIDS);
			createClass(year, month, day, 11, 15, 60, PROG_ADULTS);
			break;
		default:
			log.info("No classes to schedule on given day ("+dayOfWeek+")");
			break;
		}
	}

	private Long createClass(int year, int month, int day, int hour, int min, int durationMins, String program) {
		FkfClass cls = null;
		WpPosts post = null;
		WpDbDao dao = null;
		WpEmEvents evt = null;
		List<WpPostMeta> postMeta = null;
		WpEmTickets tick = null;

		Long postId = 0L;
		Long eventId = 0L;

		dao = new WpDbDao();
		cls = renderClass(year, month, day, hour, min, durationMins, program);
		log.info(cls.toString());
		post = map(cls);
		post = dao.saveWpPost(post);
		post.setGuid(post.getGuid() + "p=" + post.getId());
		dao.updateWpPost(post);

		evt = mapWpEvent(cls, post);
		evt.setPostId(post.getId());
		evt = dao.saveWpEmEvent(evt);

		postMeta = mapPostMeta(evt);
		dao.saveWpPostMeta(postMeta);

		tick = mapWpEmTicket(evt);
		dao.saveWpEmTicket(tick);
		
		return postId;

	}

	private WpPosts map(FkfClass cls) {
		WpPosts post = null;
		Timestamp currTime = null;

		currTime = getCurrentTime();

		post = new WpPosts();

		// Set all the static stuff
		post.setPostAuthor(EVENT_OWNER);
		post.setPostExcerpt("");
		post.setPostStatus(POST_STATUS);
		post.setCommentStatus("closed");
		post.setPingStatus("closed");
		post.setPostPassword("");
		post.setToPing("");
		post.setPinged("");
		post.setPostContentFiltered("");
		post.setPostParent(0L);
		post.setMenuOrder(0);
		post.setPostType("event");
		post.setPostMimeType("");
		post.setCommentCount(0L);

		post.setPostDate(currTime);
		post.setPostModified(currTime);
		post.setPostDateGmt(getUtcTime(currTime));
		post.setPostModifiedGmt(getUtcTime(currTime));

		post.setPostContent(derivePostContent(cls));
		post.setPostTitle(post.getPostContent());
		post.setPostName(derivePostName(cls));
		post.setGuid(deriveInitialGuid(cls));
		return post;
	}

	private WpEmEvents mapWpEvent(FkfClass cls, WpPosts post) {
		WpEmEvents evt = null;

		evt = new WpEmEvents();

		evt.setPostId(evt.getPostId());
		// evt.setEventParentId(0L);
		evt.setEventSlug(post.getPostName());
		evt.setEventOwner(EVENT_OWNER);
		evt.setEventStatus(1);
		evt.setEventName(post.getPostContent());
		evt.setEventStartDate(new java.sql.Date(cls.getStartTime().getTime()));
		evt.setEventEndDate(new java.sql.Date(cls.getEndTime().getTime()));
		evt.setEventStartTime(new Time(cls.getStartTime().getTime()));
		evt.setEventEndTime(new Time(cls.getEndTime().getTime()));
		evt.setEventAllDday(0);

		// Needs to be UTC
		evt.setEventStart(getUtcTime(cls.getStartTime()));
		evt.setEventEnd(getUtcTime(cls.getEndTime()));

		evt.setEventTimeZone("America/New_York");
		evt.setPostContent(post.getPostContent());
		evt.setEventRsvp(1);
		evt.setEventRsvpDate(new java.sql.Date(cls.getStartTime().getTime() - RSVP_WINDOW));
		evt.setEventRsvpTime(new Time(cls.getStartTime().getTime() - RSVP_WINDOW));
		evt.setEventRsvpSpaces(0);
		evt.setEventSpaces(ALLOWED_SPACES);
		evt.setEventPrivate(0);
		evt.setLocationId(2L);
//		evt.setEventLocationType("");
		evt.setEventDateCreated(post.getPostDate());
		evt.setEventDateModified(post.getPostDate());
//		evt.setRecurrenceId(0L);
//		evt.setRecurrence(0);
//		evt.setRecurrenceInterval(0);
//		evt.setRecurrenceFreq("");
//		evt.setRecurrenceByDay("");
		evt.setBlogId(0L);
		evt.setGroupId(0L);
		evt.setEventLanguage("en_US");
		evt.setEventTranslation(0);

		return evt;
	}

	private List<WpPostMeta> mapPostMeta(WpEmEvents evt) {
		List<WpPostMeta> metas = null;

		metas = new ArrayList<WpPostMeta>();

		metas.add(new WpPostMeta(evt.getPostId(), "_event_id", Long.toString(evt.getEventId())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_timezone", evt.getEventTimeZone()));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_start_time",
				dfTimeOnlyMeta.format(new java.util.Date(evt.getEventStartTime().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_end_time",
				dfTimeOnlyMeta.format(new java.util.Date(evt.getEventEndTime().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_start",
				dfFullDateTimeMeta.format(new java.util.Date(evt.getEventStart().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_end",
				dfFullDateTimeMeta.format(new java.util.Date(evt.getEventEnd().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_start_date",
				dfDateOnlyMeta.format(new java.util.Date(evt.getEventStartDate().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_end_date",
				dfDateOnlyMeta.format(new java.util.Date(evt.getEventEndDate().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_rsvp", Integer.toString(evt.getEventRsvp())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_rsvp_date",
				dfDateOnlyMeta.format(new java.util.Date(evt.getEventRsvpDate().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_rsvp_time",
				dfTimeOnlyMeta.format(new java.util.Date(evt.getEventRsvpDate().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_rsvp_spaces", Integer.toString(evt.getEventRsvpSpaces())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_spaces", Integer.toString(evt.getEventSpaces())));
		metas.add(new WpPostMeta(evt.getPostId(), "_location_id", Long.toString(evt.getLocationId())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_location_type", evt.getEventLocationType()));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_start_local",
				dfDateOnlyMeta.format(new java.util.Date(evt.getEventStartDate().getTime())) + " "
						+ dfTimeOnlyMeta.format(new java.util.Date(evt.getEventStartDate().getTime()))));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_end_local",
				dfDateOnlyMeta.format(new java.util.Date(evt.getEventEndDate().getTime())) + " "
						+ dfTimeOnlyMeta.format(new java.util.Date(evt.getEventEndDate().getTime()))));
		//metas.add(new WpPostMeta(evt.getPostId(), "_event_status", Integer.toString(evt.getEventStatus())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_language", evt.getEventLanguage()));
		// metas.add(new WpPostMeta(post.getId(),"_wp_old_slug",""));

		return metas;
	}
	
	private WpEmTickets mapWpEmTicket(WpEmEvents evt)
	{
		WpEmTickets tick = null;
		
		tick = new WpEmTickets(evt.getEventId(), "Student Reservation", 0.00d, ALLOWED_SPACES, 1);
		
		return tick;
	}

	private String deriveInitialGuid(FkfClass cls) {

		// https://www.fairportkungfu.com/?post_type=event&#038;p=3611

		StringBuilder sb = new StringBuilder();

		sb.append("https://www.fairportkungfu.com/?post_type=event&#038;");

		return sb.toString();
	}

	private String derivePostContent(FkfClass cls) {

		// 11/05/2020 : Thu : Kids : 6:25pm to 7:00pm

		StringBuilder sb = new StringBuilder();
		Date startDate = null;
		Date endDate = null;
		String fs = " : ";

		startDate = new Date(cls.getStartTime().getTime());
		endDate = new Date(cls.getEndTime().getTime());

		sb.append(dfDateOnly.format(startDate)).append(fs);
		sb.append(dfWeekday.format(startDate)).append(fs);
		sb.append(cls.getProgram()).append(fs);
		sb.append(dfTime.format(startDate).toLowerCase()).append(" to ");
		sb.append(dfTime.format(endDate).toLowerCase());

		return sb.toString();
	}

	private String derivePostName(FkfClass cls) {

		// 11-19-2020-thu-kids-535pm-to-610pm

		StringBuilder sb = new StringBuilder();
		Date startDate = null;
		Date endDate = null;
		String fs = "-";

		startDate = new Date(cls.getStartTime().getTime());
		endDate = new Date(cls.getEndTime().getTime());

		sb.append(dfDateOnlyDashes.format(startDate)).append(fs);
		sb.append(dfWeekday.format(startDate).toLowerCase()).append(fs);
		sb.append(cls.getProgram().toLowerCase()).append(fs);
		sb.append(dfTimeCompressed.format(startDate).toLowerCase()).append("-to-");
		sb.append(dfTimeCompressed.format(endDate).toLowerCase());

		return sb.toString();
	}

	private Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	private Timestamp getUtcTime(Timestamp time) {
		Timestamp utcTime = null;

		// Note we SUBTRACT the offset to get back to UTC
		utcTime = new Timestamp(time.getTime() - TIME_ZONE.getOffset(time.getTime()));

		return utcTime;
	}

	public FkfClass renderClass(int year, int month, int day, int hour, int min, int durationMins, String program) {
		Calendar startCal = null;

		Timestamp start = null;
		Timestamp end = null;

		startCal = new Calendar.Builder().setCalendarType("iso8601").setDate(year, month - 1, day)
				.setTimeOfDay(hour, min, 0).build();
		startCal.setTimeZone(TimeZone.getTimeZone("GMT"));
		start = new Timestamp(startCal.getTime().getTime());
		end = new Timestamp(start.getTime() + (durationMins * 60 * 1000));

		return new FkfClass(program, start, end);
	}
}
