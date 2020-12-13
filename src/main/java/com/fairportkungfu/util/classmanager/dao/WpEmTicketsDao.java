package com.fairportkungfu.util.classmanager.dao;

import javax.persistence.Query;

import com.fairportkungfu.util.classmanager.model.WpEmTickets;

public class WpEmTicketsDao extends BaseDao {

	
	public WpEmTickets findByPostId(Long eventId) {
		
		init();
		Query q = em.createNamedQuery("WpEmTickets.findByEventId");
		WpEmTickets ticket = null;

		q.setParameter("eventId", eventId);
		ticket = (WpEmTickets) q.getSingleResult();
		deinit();

		return ticket;
	}
	
	public WpEmTickets save(WpEmTickets tick) {
		init();

		em.getTransaction().begin();
		em.persist(tick);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return tick;
	}
}
