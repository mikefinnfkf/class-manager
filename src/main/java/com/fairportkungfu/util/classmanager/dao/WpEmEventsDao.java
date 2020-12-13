package com.fairportkungfu.util.classmanager.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.Query;

import com.fairportkungfu.util.classmanager.model.WpEmEvents;

public class WpEmEventsDao extends BaseDao {

	public WpEmEvents findByPostId(Long postId) {

		init();
		Query q = em.createNamedQuery("WpEmEvents.findByPostId");
		WpEmEvents event = null;

		q.setParameter("postId", postId);
		event = (WpEmEvents) q.getSingleResult();
		deinit();

		return event;
	}

	@SuppressWarnings("unchecked")
	public List<WpEmEvents> querybyEventDate(Date eventDate) {

		Query q = null;
		List<WpEmEvents> events = null;
		
		init();
		q = em.createNamedQuery("WpEmEvents.queryByDate");
		q.setParameter("startDate", eventDate);
		q.setParameter("endDate", eventDate);
		events = (List<WpEmEvents>)q.getResultList();
		deinit();
		return events;
		
	}

	public WpEmEvents save(WpEmEvents evt) {
		init();
		em.getTransaction().begin();
		em.persist(evt);
		em.getTransaction().commit();
		deinit();
		return evt;
	}

}
