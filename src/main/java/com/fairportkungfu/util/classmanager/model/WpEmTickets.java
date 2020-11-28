package com.fairportkungfu.util.classmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_em_tickets")
public class WpEmTickets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "ticket_name")
	private String ticketName;

	@Column(name = "ticket_description")
	private String ticketDescription;

	@Column(name = "ticket_price")
	private Double ticketPrice;

	@Column(name = "ticket_start")
	private Timestamp ticketStart;

	@Column(name = "ticket_end")
	private Timestamp ticketEnd;

	@Column(name = "ticket_min")
	private int ticketMin;

	@Column(name = "ticket_max")
	private int ticketMax;

	@Column(name = "ticket_spaces")
	private int ticketSpaces;

	@Column(name = "ticket_members")
	private int ticketMembers;

	@Column(name = "ticket_members_roles")
	private String ticketMembersRoles;

	@Column(name = "ticket_guests")
	private int ticketGuests;
	
	@Column(name = "ticket_required")
	private int ticketRequired;
	
	@Column(name = "ticket_parent")
	private Long ticketParent;
	
	@Column(name = "ticket_order")
	private int ticketOrder;
	
	@Column(name = "ticket_meta")
	private String ticketMeta;

	public WpEmTickets() {
		// TODO Auto-generated constructor stub
	}

	public WpEmTickets(Long eventId, String ticketName, Double ticketPrice, int ticketSpaces, int ticketOrder) {
		super();
		this.eventId = eventId;
		this.ticketName = ticketName;
		this.ticketPrice = ticketPrice;
		this.ticketSpaces = ticketSpaces;
		this.ticketOrder = ticketOrder;
	}



	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Timestamp getTicketStart() {
		return ticketStart;
	}

	public void setTicketStart(Timestamp ticketStart) {
		this.ticketStart = ticketStart;
	}

	public Timestamp getTicketEnd() {
		return ticketEnd;
	}

	public void setTicketEnd(Timestamp ticketEnd) {
		this.ticketEnd = ticketEnd;
	}

	public int getTicketMin() {
		return ticketMin;
	}

	public void setTicketMin(int ticketMin) {
		this.ticketMin = ticketMin;
	}

	public int getTicketMax() {
		return ticketMax;
	}

	public void setTicketMax(int ticketMax) {
		this.ticketMax = ticketMax;
	}

	public int getTicketSpaces() {
		return ticketSpaces;
	}

	public void setTicketSpaces(int ticketSpaces) {
		this.ticketSpaces = ticketSpaces;
	}

	public int getTicketMembers() {
		return ticketMembers;
	}

	public void setTicketMembers(int ticketMembers) {
		this.ticketMembers = ticketMembers;
	}

	public String getTicketMembersRoles() {
		return ticketMembersRoles;
	}

	public void setTicketMembersRoles(String ticketMembersRoles) {
		this.ticketMembersRoles = ticketMembersRoles;
	}

	public int getTicketGuests() {
		return ticketGuests;
	}

	public void setTicketGuests(int ticketGuests) {
		this.ticketGuests = ticketGuests;
	}

	public int getTicketRequired() {
		return ticketRequired;
	}

	public void setTicketRequired(int ticketRequired) {
		this.ticketRequired = ticketRequired;
	}

	public Long getTicketParent() {
		return ticketParent;
	}

	public void setTicketParent(Long ticketParent) {
		this.ticketParent = ticketParent;
	}

	public int getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(int ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public String getTicketMeta() {
		return ticketMeta;
	}

	public void setTicketMeta(String ticketMeta) {
		this.ticketMeta = ticketMeta;
	}

	
}
