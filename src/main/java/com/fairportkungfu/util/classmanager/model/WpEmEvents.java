package com.fairportkungfu.util.classmanager.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * JPA/Hibernate entity class
 * 
 * For table wp_em_events. Holds event detail for WP Event Manager events.
 * 
 * @author Mike Finn <mike@fairportkungfu.com>
 *
 */
@Entity
@Table(name = "wp_em_events")
@NamedQuery(name = "WpEmEvents.findByPostId", query = "select p from WpEmEvents p where p.postId = :postId")
@NamedQuery(name = "WpEmEvents.queryByDate", query = "select p from WpEmEvents p where p.eventStartDate >= :startDate and p.eventStartDate <= :endDate")
public class WpEmEvents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "event_parent")
	private Long eventParentId;

	@Column(name = "event_slug")
	private String eventSlug;
	@Column(name = "event_owner")
	private Long eventOwner;
	@Column(name = "event_status")
	private int eventStatus = 1;
	@Column(name = "event_name")
	private String eventName;
	@Column(name = "event_start_date")
	private Date eventStartDate;
	@Column(name = "event_end_date")
	private Date eventEndDate;
	@Column(name = "event_start_time")
	private Time eventStartTime;
	@Column(name = "event_end_time")
	private Time eventEndTime;
	@Column(name = "event_all_day")
	private int eventAllDday;
	@Column(name = "event_start")
	private Timestamp eventStart;
	@Column(name = "event_end")
	private Timestamp eventEnd;
	@Column(name = "event_timezone")
	private String eventTimeZone = "America/New_York";
	@Column(name = "post_content")
	private String postContent;
	@Column(name = "event_rsvp")
	private int eventRsvp;
	@Column(name = "event_rsvp_date")
	private Date eventRsvpDate;
	@Column(name = "event_rsvp_time")
	private Time eventRsvpTime;
	@Column(name = "event_rsvp_spaces")
	private int eventRsvpSpaces;
	@Column(name = "event_spaces")
	private int eventSpaces;
	@Column(name = "event_private")
	private int eventPrivate;
	@Column(name = "location_id")
	private Long locationId;
	@Column(name = "event_location_type")
	private String eventLocationType;
	@Column(name = "recurrence_id")
	private Long recurrenceId;
	@Column(name = "event_date_created")
	private Timestamp eventDateCreated;
	@Column(name = "event_date_modified")
	private Timestamp eventDateModified;
	@Column(name = "recurrence")
	private int recurrence;
	@Column(name = "recurrence_interval")
	private int recurrenceInterval;
	@Column(name = "recurrence_freq")
	private String recurrenceFreq;
	@Column(name = "recurrence_byday")
	private String recurrenceByDay;
	@Column(name = "recurrence_byweekno")
	private int recurrenceByWeekNo;
	@Column(name = "recurrence_days")
	private int recurrenceDays;
	@Column(name = "recurrence_rsvp_days")
	private int recurrenceRsvpDays;
	@Column(name = "blog_id")
	private Long blogId;
	@Column(name = "group_id")
	private Long groupId;
	@Column(name = "event_language")
	private String eventLanguage = "en_US";
	@Column(name = "event_translation")
	private int eventTranslation;

	public WpEmEvents() {
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventid) {
		this.eventId = eventid;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getEventParentId() {
		return eventParentId;
	}

	public void setEventParentId(Long eventParentId) {
		this.eventParentId = eventParentId;
	}

	public String getEventSlug() {
		return eventSlug;
	}

	public void setEventSlug(String eventSlug) {
		this.eventSlug = eventSlug;
	}

	public Long getEventOwner() {
		return eventOwner;
	}

	public void setEventOwner(Long eventOwner) {
		this.eventOwner = eventOwner;
	}

	public int getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(int eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Date getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public Time getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Time eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Time getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Time eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public int getEventAllDday() {
		return eventAllDday;
	}

	public void setEventAllDday(int eventAllDday) {
		this.eventAllDday = eventAllDday;
	}

	public Timestamp getEventStart() {
		return eventStart;
	}

	public void setEventStart(Timestamp eventStart) {
		this.eventStart = eventStart;
	}

	public Timestamp getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(Timestamp eventEnd) {
		this.eventEnd = eventEnd;
	}

	public String getEventTimeZone() {
		return eventTimeZone;
	}

	public void setEventTimeZone(String eventTimeZone) {
		this.eventTimeZone = eventTimeZone;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public int getEventRsvp() {
		return eventRsvp;
	}

	public void setEventRsvp(int eventRsvp) {
		this.eventRsvp = eventRsvp;
	}

	public Date getEventRsvpDate() {
		return eventRsvpDate;
	}

	public void setEventRsvpDate(Date eventRsvpDate) {
		this.eventRsvpDate = eventRsvpDate;
	}

	public Time getEventRsvpTime() {
		return eventRsvpTime;
	}

	public void setEventRsvpTime(Time eventRsvpTime) {
		this.eventRsvpTime = eventRsvpTime;
	}

	public int getEventRsvpSpaces() {
		return eventRsvpSpaces;
	}

	public void setEventRsvpSpaces(int rsvpSpaces) {
		this.eventRsvpSpaces = rsvpSpaces;
	}

	public int getEventSpaces() {
		return eventSpaces;
	}

	public void setEventSpaces(int eventSpaces) {
		this.eventSpaces = eventSpaces;
	}

	public int getEventPrivate() {
		return eventPrivate;
	}

	public void setEventPrivate(int eventPrivate) {
		this.eventPrivate = eventPrivate;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getEventLocationType() {
		return eventLocationType;
	}

	public void setEventLocationType(String eventLocationType) {
		this.eventLocationType = eventLocationType;
	}

	public Long getRecurrenceId() {
		return recurrenceId;
	}

	public void setRecurrenceId(Long recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public Timestamp getEventDateCreated() {
		return eventDateCreated;
	}

	public void setEventDateCreated(Timestamp eventDateCreated) {
		this.eventDateCreated = eventDateCreated;
	}

	public Timestamp getEventDateModified() {
		return eventDateModified;
	}

	public void setEventDateModified(Timestamp eventDateModified) {
		this.eventDateModified = eventDateModified;
	}

	public int getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(int recurrence) {
		this.recurrence = recurrence;
	}

	public int getRecurrenceInterval() {
		return recurrenceInterval;
	}

	public void setRecurrenceInterval(int recurrenceInterval) {
		this.recurrenceInterval = recurrenceInterval;
	}

	public String getRecurrenceFreq() {
		return recurrenceFreq;
	}

	public void setRecurrenceFreq(String recurrenceFreq) {
		this.recurrenceFreq = recurrenceFreq;
	}

	public int getRecurrenceByWeekNo() {
		return recurrenceByWeekNo;
	}

	public void setRecurrenceByWeekNo(int recurrenceByWeekNo) {
		this.recurrenceByWeekNo = recurrenceByWeekNo;
	}

	public int getRecurrenceDays() {
		return recurrenceDays;
	}

	public void setRecurrenceDays(int recurrenceDays) {
		this.recurrenceDays = recurrenceDays;
	}

	public int getRecurrenceRsvpDays() {
		return recurrenceRsvpDays;
	}

	public void setRecurrenceRsvpDays(int recurrenceRsvpDays) {
		this.recurrenceRsvpDays = recurrenceRsvpDays;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getEventLanguage() {
		return eventLanguage;
	}

	public void setEventLanguage(String eventLanguage) {
		this.eventLanguage = eventLanguage;
	}

	public int getEventTranslation() {
		return eventTranslation;
	}

	public void setEventTranslation(int eventTranslation) {
		this.eventTranslation = eventTranslation;
	}

	public String getRecurrenceByDay() {
		return recurrenceByDay;
	}

	public void setRecurrenceByDay(String recurrenceByDay) {
		this.recurrenceByDay = recurrenceByDay;
	}

	@Override
	public String toString() {
		return "WpEmEvents [eventid=" + eventId + ", postId=" + postId + ", eventParentId=" + eventParentId
				+ ", eventSlug=" + eventSlug + ", eventOwner=" + eventOwner + ", eventStatus=" + eventStatus
				+ ", eventName=" + eventName + ", eventStartDate=" + eventStartDate + ", eventEndDate=" + eventEndDate
				+ ", eventStartTime=" + eventStartTime + ", eventEndTime=" + eventEndTime + ", eventAllDday="
				+ eventAllDday + ", eventStart=" + eventStart + ", eventEnd=" + eventEnd + ", eventTimeZone="
				+ eventTimeZone + ", postContent=" + postContent + ", eventRsvp=" + eventRsvp + ", eventRsvpDate="
				+ eventRsvpDate + ", eventRsvpTime=" + eventRsvpTime + ", rsvpSpaces=" + eventRsvpSpaces + ", eventSpaces="
				+ eventSpaces + ", eventPrivate=" + eventPrivate + ", locationId=" + locationId + ", eventLocationType="
				+ eventLocationType + ", recurrenceId=" + recurrenceId + ", eventDateCreated=" + eventDateCreated
				+ ", eventDateModified=" + eventDateModified + ", recurrence=" + recurrence + ", recurrenceInterval="
				+ recurrenceInterval + ", recurrenceFreq=" + recurrenceFreq + ", recurrenceByWeekNo="
				+ recurrenceByWeekNo + ", recurrenceDays=" + recurrenceDays + ", recurrenceRsvpDays="
				+ recurrenceRsvpDays + ", blogId=" + blogId + ", groupId=" + groupId + ", eventLanguage="
				+ eventLanguage + ", eventTranslation=" + eventTranslation + "]";
	}

}
