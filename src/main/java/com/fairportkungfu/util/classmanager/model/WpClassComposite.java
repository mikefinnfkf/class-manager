package com.fairportkungfu.util.classmanager.model;

import java.util.List;

public class WpClassComposite {

	private WpEmEvents event;
	private WpPosts post;
	private WpEmTickets ticket;
	private List<WpPostMeta> postMeta;

	public WpClassComposite() {
		// TODO Auto-generated constructor stub
	}

	public WpEmEvents getEvent() {
		return event;
	}

	public void setEvent(WpEmEvents event) {
		this.event = event;
	}

	public WpPosts getPost() {
		return post;
	}

	public void setPost(WpPosts post) {
		this.post = post;
	}

	public WpEmTickets getTicket() {
		return ticket;
	}

	public void setTicket(WpEmTickets ticket) {
		this.ticket = ticket;
	}

	public List<WpPostMeta> getPostMeta() {
		return postMeta;
	}

	public void setPostMeta(List<WpPostMeta> postMeta) {
		this.postMeta = postMeta;
	}

	@Override
	public String toString() {
		return "WpClassComposite [event=" + event + ", post=" + post + ", ticket=" + ticket + ", postMeta=" + postMeta
				+ "]";
	}

}
