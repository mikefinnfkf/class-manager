package com.fairportkungfu.util.eventcreator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fairportkungfu.util.eventcreator.model.WpEmEvents;
import com.fairportkungfu.util.eventcreator.model.WpEmTickets;
import com.fairportkungfu.util.eventcreator.model.WpPostMeta;
import com.fairportkungfu.util.eventcreator.model.WpPosts;

public class WpDbDao {

	private static final String PU_NAME = "main-prod";
	
	public WpDbDao() {
		// TODO Auto-generated constructor stub
	}

	public WpPosts saveWpPost(WpPosts post) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(post);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return post;
	}
	
	public WpPosts updateWpPost(WpPosts post) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.merge(post);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return post;
	}
	
	public WpEmEvents saveWpEmEvent(WpEmEvents evt) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(evt);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return evt;
	}
	
	public void saveWpPostMeta(List<WpPostMeta> meta)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		for (WpPostMeta m : meta) {
			em.persist(m);
			em.flush();
			em.clear();
		}
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		
	}
	
	public WpEmTickets saveWpEmTicket(WpEmTickets tick) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(tick);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return tick;
	}
}
