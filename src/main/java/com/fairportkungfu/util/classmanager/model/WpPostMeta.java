package com.fairportkungfu.util.classmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_postmeta")
public class WpPostMeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meta_id")
	private Long metaId;

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "meta_key")
	private String meta_key;

	@Column(name = "meta_value")
	private String meta_value;

	public WpPostMeta() {
		// TODO Auto-generated constructor stub
	}
	
	

	public WpPostMeta(Long postId, String meta_key, String meta_value) {
		super();
		this.postId = postId;
		this.meta_key = meta_key;
		this.meta_value = meta_value;
	}



	public Long getMetaId() {
		return metaId;
	}

	public void setMetaId(Long metaId) {
		this.metaId = metaId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getMeta_key() {
		return meta_key;
	}

	public void setMeta_key(String meta_key) {
		this.meta_key = meta_key;
	}

	public String getMeta_value() {
		return meta_value;
	}

	public void setMeta_value(String meta_value) {
		this.meta_value = meta_value;
	}

}
