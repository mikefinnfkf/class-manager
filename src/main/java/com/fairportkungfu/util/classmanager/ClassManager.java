package com.fairportkungfu.util.classmanager;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.fairportkungfu.util.classmanager.dao.WpEmEventsDao;
import com.fairportkungfu.util.classmanager.dao.WpEmTicketsDao;
import com.fairportkungfu.util.classmanager.dao.WpPostMetaDao;
import com.fairportkungfu.util.classmanager.dao.WpPostsDao;
import com.fairportkungfu.util.classmanager.model.FkfClass;
import com.fairportkungfu.util.classmanager.model.WpClassComposite;
import com.fairportkungfu.util.classmanager.model.WpEmEvents;
import com.fairportkungfu.util.classmanager.model.WpEmTickets;
import com.fairportkungfu.util.classmanager.model.WpPostMeta;
import com.fairportkungfu.util.classmanager.model.WpPosts;

public class ClassManager {

	private static DateFormat dfDateOnly = new SimpleDateFormat("MM/dd/yyyy");
	private static DateFormat dfDateOnlyDashes = new SimpleDateFormat("MM-dd-yyyy");
	private static DateFormat dfWeekday = new SimpleDateFormat("EEE");
	private static DateFormat dfTime = new SimpleDateFormat("hh:mma");
	private static DateFormat dfTimeCompressed = new SimpleDateFormat("hhmma");
	private static DateFormat dfFullDateTimeMeta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat dfFullDateTimeLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
	private static DateFormat dfDateOnlyMeta = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat dfDateOnlyLog = new SimpleDateFormat("yyyy-MM-dd EEE");
	private static DateFormat dfTimeOnlyMeta = new SimpleDateFormat("HH:mm:ss");
	private static final String TIME_ZONE_ID = "EST5EDT";
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone(TIME_ZONE_ID);
	//private static final Long RSVP_WINDOW = 60L * 60L * 1000L * 1L; // 1 hours
	// MJF 04/25/2021 - No RSVP window
	private static final Long RSVP_WINDOW = 0L;
	private static final Long EVENT_OWNER = 4L;
	private static final Integer ALLOWED_SPACES = 8;
	private static final String PROG_TIGERS = "Tigers";
	private static final String PROG_KIDS = "Kids";
	private static final String PROG_ADULTS = "Adults";
	@SuppressWarnings("unused")
	private static final String PROG_TEST = "TEST_DONOTUSE";
	private static final int DUR_TIGERS = 20;
	private static final int DUR_KIDS = 35;
	private static final int DUR_ADULTS_DEFAULT = 60;
	private static final int DUR_ADULTS_SUNDAY = 60;

	// Looks weird, but we don't want the classes to show up for registration until
	// enabled. Just restore in the console.
	private static final String POST_STATUS = "publish";

	private Logger log = Logger.getLogger(getClass().getName());

	public ClassManager() {

	}

	public void createClasses(Date date) throws Exception {
		Calendar dateCalClasses = null;
		Calendar dateCalNow = null;
		int dayOfWeek;
		boolean isDateConflict = true;

		log.info("Date is " + dfDateOnlyLog.format(date));


		// Convert to Date cal b/c it's easier to deal with the parts of date and time
		dateCalClasses = new Calendar.Builder().setInstant(date).build();
		dateCalNow = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();

		log.info("Preflight check: make sure there are no classes defined for given date");
		isDateConflict = existsClassByDate(date);
		log.info(" - Classes exist for given date: " + isDateConflict);
		if (isDateConflict) {
			log.severe("Failed preflight check for existing classes on date. Aborting.");
			throw new Exception("Classes already exist for date: "+dfDateOnlyLog.format(date));
		} else
			log.info(" - PASS");

		log.info("Preflight check: given date and time is later than now");
		log.info(" - Given time: " + dfFullDateTimeLog.format(dateCalClasses.getTime()));
		log.info(" - Now       : " + dfFullDateTimeLog.format(dateCalNow.getTime()));

		if (dateCalClasses.before(dateCalNow)) {
			log.severe("Failed preflight check for date. Aborting.");
			throw new Exception("Date must be in future");
		} else
			log.info(" - PASS");

		log.info("Creating classes for: " + dfDateOnlyLog.format(dateCalClasses.getTime()) + ": START");

		// What day of week is it?
		dayOfWeek = dateCalClasses.get(Calendar.DAY_OF_WEEK);
		Builder builder = new Calendar.Builder();
		builder.set(Calendar.MONTH, dateCalClasses.get(Calendar.MONTH));
		builder.set(Calendar.DAY_OF_MONTH, dateCalClasses.get(Calendar.DAY_OF_MONTH));
		builder.set(Calendar.YEAR, dateCalClasses.get(Calendar.YEAR));

		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			createClass(builder.setTimeOfDay(8, 30, 0).build(), DUR_ADULTS_SUNDAY, PROG_ADULTS);
			break;
		case Calendar.TUESDAY:
			createClass(builder.setTimeOfDay(17, 0, 0).build(), DUR_TIGERS, PROG_TIGERS);
			createClass(builder.setTimeOfDay(17, 35, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(18, 25, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(19, 15, 0).build(), DUR_ADULTS_DEFAULT, PROG_ADULTS);
			break;
		case Calendar.THURSDAY:
			createClass(builder.setTimeOfDay(17, 0, 0).build(), DUR_TIGERS, PROG_TIGERS);
			createClass(builder.setTimeOfDay(17, 35, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(18, 25, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(19, 15, 0).build(), DUR_ADULTS_DEFAULT, PROG_ADULTS);
			break;
		case Calendar.SATURDAY:
			createClass(builder.setTimeOfDay(9, 0, 0).build(), DUR_TIGERS, PROG_TIGERS);
			createClass(builder.setTimeOfDay(9, 35, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(10, 25, 0).build(), DUR_KIDS, PROG_KIDS);
			createClass(builder.setTimeOfDay(11, 15, 0).build(), DUR_ADULTS_DEFAULT, PROG_ADULTS);
			break;
		default:
			log.info("No classes to schedule on given day (" + dayOfWeek + ")");
			break;
		}

		log.info("Creating classes for: " + dfDateOnlyLog.format(dateCalClasses.getTime()) + ": END");

	}

	public boolean existsClassByDate(Date date) {
		WpEmEventsDao evtDao = null;

		evtDao = new WpEmEventsDao();

		return (evtDao.querybyEventDate(new java.sql.Date(date.getTime())).size() > 0);
	}

	public List<WpClassComposite> queryClassByDate(Date date) {

		List<WpClassComposite> dbClassComposites = null;
		List<WpEmEvents> events = null;
		WpEmEventsDao evtDao = null;
		Set<Long> postIds = null;

		dbClassComposites = new ArrayList<WpClassComposite>();
		evtDao = new WpEmEventsDao();
		postIds = new TreeSet<Long>();

		// Get the events for the given day and build a list of post ids so we can query
		// by post
		events = evtDao.querybyEventDate(new java.sql.Date(date.getTime()));
		for (WpEmEvents event : events) {
			postIds.add(event.getPostId());
		}

		// Query for each post
		for (Long postId : postIds) {
			dbClassComposites.add(queryClassByPostId(postId));
		}

		return dbClassComposites;
	}

	public WpClassComposite queryClassByPostId(Long id) {
		WpClassComposite dbClassComposite = null;

		dbClassComposite = new WpClassComposite();
		dbClassComposite.setPost(queryWpPost(id));

		// Get the event and postmeta from post id
		if (dbClassComposite.getPost() != null) {
			dbClassComposite.setEvent(queryWpEmEvent(dbClassComposite.getPost().getId()));
			dbClassComposite.setPostMeta(queryWpPostMeta(dbClassComposite.getPost().getId()));
		}

		// Get ticket from event id
		if (dbClassComposite.getEvent() != null) {
			dbClassComposite.setTicket(queryWpEmTicket(dbClassComposite.getEvent().getEventId()));
		}

		return dbClassComposite;
	}

	private WpPosts queryWpPost(Long id) {
		WpPosts post = null;
		WpPostsDao dao = null;

		dao = new WpPostsDao();

		post = dao.findById(id);

		return post;
	}

	private WpEmEvents queryWpEmEvent(Long postId) {
		WpEmEvents event = null;
		WpEmEventsDao dao = null;

		dao = new WpEmEventsDao();
		event = dao.findByPostId(postId);
		return event;
	}

	private WpEmTickets queryWpEmTicket(Long eventId) {
		WpEmTickets ticket = null;
		WpEmTicketsDao dao = null;

		dao = new WpEmTicketsDao();
		ticket = dao.findByPostId(eventId);
		return ticket;
	}

	private List<WpPostMeta> queryWpPostMeta(Long postId) {
		List<WpPostMeta> postMeta = null;
		WpPostMetaDao dao = null;

		dao = new WpPostMetaDao();
		postMeta = dao.queryByPostId(postId);
		return postMeta;
	}

	private Long createClass(Calendar classTime, int durationMins, String program) {
		FkfClass cls = null;
		WpPosts post = null;
		WpPostsDao postsDao = null;
		WpEmEventsDao eventsDao = null;
		WpPostMetaDao postMetaDao = null;
		WpEmTicketsDao ticketsDao = null;

		WpEmEvents evt = null;
		List<WpPostMeta> postMeta = null;
		WpEmTickets tick = null;

		Long postId = 0L;
		Long eventId = 0L;

		postsDao = new WpPostsDao();
		eventsDao = new WpEmEventsDao();
		postMetaDao = new WpPostMetaDao();
		ticketsDao = new WpEmTicketsDao();

		cls = renderClass(classTime, durationMins, program);

		log.info(" - Create class for: " + cls.toString());
		post = map(cls);
		post = postsDao.saveWpPost(post);
		post.setGuid(post.getGuid() + "p=" + post.getId());
		postsDao.updateWpPost(post);

		evt = mapWpEvent(cls, post);
		evt.setPostId(post.getId());
		evt = eventsDao.save(evt);

		postMeta = mapPostMeta(evt);
		postMetaDao.save(postMeta);

		tick = mapWpEmTicket(evt);
		ticketsDao.save(tick);

		return postId;

	}

	private FkfClass map(WpClassComposite ec) {
		FkfClass cls = null;

		cls = new FkfClass();

		// cls.setProgram(ec);

		return cls;
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
		// metas.add(new WpPostMeta(evt.getPostId(), "_event_status",
		// Integer.toString(evt.getEventStatus())));
		metas.add(new WpPostMeta(evt.getPostId(), "_event_language", evt.getEventLanguage()));
		// metas.add(new WpPostMeta(post.getId(),"_wp_old_slug",""));

		return metas;
	}

	private WpEmTickets mapWpEmTicket(WpEmEvents evt) {
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

	private FkfClass renderClass(Calendar startCal, int durationMins, String program) {

		Timestamp start = null;
		Timestamp end = null;

		startCal.setTimeZone(TimeZone.getTimeZone("GMT"));
		start = new Timestamp(startCal.getTime().getTime());
		end = new Timestamp(start.getTime() + (durationMins * 60 * 1000));

		return new FkfClass(program, start, end);
	}
}
