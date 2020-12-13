package com.fairportkungfu.util.classmanager.dao;

import java.util.List;

import javax.persistence.Query;

import com.fairportkungfu.util.classmanager.model.WpPostMeta;

public class WpPostMetaDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<WpPostMeta> queryByPostId(Long postId) {
		
		init();
		Query q = em.createNamedQuery("WpPostMeta.queryByPostId");
		List<WpPostMeta> postMeta = null;
		
		q.setParameter("postId", postId);
		postMeta = (List<WpPostMeta>) q.getResultList();
		deinit();

		return postMeta;
	}
	
	public void save(List<WpPostMeta> meta) {
		init();
		em.getTransaction().begin();

		for (WpPostMeta m : meta) {
			em.persist(m);
			em.flush();
			em.clear();
		}
		em.getTransaction().commit();
		deinit();
	}

}
