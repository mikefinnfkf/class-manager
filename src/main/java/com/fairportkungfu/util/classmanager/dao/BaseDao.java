package com.fairportkungfu.util.classmanager.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDao {

	// TODO Property-ize this
	protected static final String PU_NAME = "main-prod";
	
	protected EntityManagerFactory emf;
	protected EntityManager em;
	
	protected void init() {
		emf = Persistence.createEntityManagerFactory(PU_NAME);
		em = emf.createEntityManager();
	}
	
	protected void deinit() {
		try { em.close(); } catch (Exception e) {}
		try { emf.close(); } catch (Exception e) {}
	}
}
