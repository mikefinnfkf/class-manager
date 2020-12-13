package com.fairportkungfu.util.classmanager.dao;

import javax.persistence.Query;

import com.fairportkungfu.util.classmanager.model.WpPosts;

public class WpPostsDao extends BaseDao {

	public WpPosts findById(Long id) {
		
		init();
		Query q = em.createNamedQuery("WpPosts.findById");
		WpPosts post = null;

		q.setParameter("id", id);
		post = (WpPosts) q.getSingleResult();
		deinit();

		return post;
	}

	public WpPosts saveWpPost(WpPosts post) {
		
		init();
		em.getTransaction().begin();
		em.persist(post);
		em.getTransaction().commit();
		deinit();
		return post;
	}

	public WpPosts updateWpPost(WpPosts post) {
		
		init();
		em.getTransaction().begin();
		em.merge(post);
		em.getTransaction().commit();
		deinit();
		return post;
	}

}
