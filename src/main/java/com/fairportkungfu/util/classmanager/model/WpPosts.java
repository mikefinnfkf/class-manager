package com.fairportkungfu.util.classmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA/Hibernate entity class
 * 
 * For table wp_em_events. Holds WP Post info.
 * 
 * @author Mike Finn <mike@fairportkungfu.com>
 *
 */
@Entity
@Table(name = "wp_posts")
public class WpPosts {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "post_author")
	private Long postAuthor;

	@Column(name = "post_date")
	private Timestamp postDate;

	@Column(name = "post_date_gmt")
	private Timestamp postDateGmt;

	@Column(name = "post_content")
	private String postContent;

	@Column(name = "post_title")
	private String postTitle;

	@Column(name = "post_excerpt")
	private String postExcerpt;

	@Column(name = "post_status")
	private String postStatus;

	@Column(name = "comment_status")
	private String commentStatus;

	@Column(name = "ping_status")
	private String pingStatus;

	@Column(name = "post_password")
	private String postPassword;

	@Column(name = "post_name")
	private String postName;

	@Column(name = "to_ping")
	private String toPing;

	@Column(name = "pinged")
	private String pinged;

	@Column(name = "post_modified")
	private Timestamp postModified;

	@Column(name = "post_modified_gmt")
	private Timestamp postModifiedGmt;

	@Column(name = "post_content_filtered")
	private String postContentFiltered;

	@Column(name = "post_parent")
	private Long postParent;

	@Column(name = "guid")
	private String guid;

	@Column(name = "menu_order")
	private int menuOrder;

	@Column(name = "post_type")
	private String postType;

	@Column(name = "post_mime_type")
	private String postMimeType;

	@Column(name = "comment_count")
	private Long commentCount;

	public WpPosts() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostAuthor() {
		return postAuthor;
	}

	public void setPostAuthor(Long postAuthor) {
		this.postAuthor = postAuthor;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public Timestamp getPostDateGmt() {
		return postDateGmt;
	}

	public void setPostDateGmt(Timestamp postDateGmt) {
		this.postDateGmt = postDateGmt;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostExcerpt() {
		return postExcerpt;
	}

	public void setPostExcerpt(String postExcerpt) {
		this.postExcerpt = postExcerpt;
	}

	public String getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public String getPingStatus() {
		return pingStatus;
	}

	public void setPingStatus(String pingStatus) {
		this.pingStatus = pingStatus;
	}

	public String getPostPassword() {
		return postPassword;
	}

	public void setPostPassword(String postPassword) {
		this.postPassword = postPassword;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getToPing() {
		return toPing;
	}

	public void setToPing(String toPing) {
		this.toPing = toPing;
	}

	public String getPinged() {
		return pinged;
	}

	public void setPinged(String pinged) {
		this.pinged = pinged;
	}

	public Timestamp getPostModified() {
		return postModified;
	}

	public void setPostModified(Timestamp postModified) {
		this.postModified = postModified;
	}

	public Timestamp getPostModifiedGmt() {
		return postModifiedGmt;
	}

	public void setPostModifiedGmt(Timestamp postModifiedGmt) {
		this.postModifiedGmt = postModifiedGmt;
	}

	public String getPostContentFiltered() {
		return postContentFiltered;
	}

	public void setPostContentFiltered(String postContentFiltered) {
		this.postContentFiltered = postContentFiltered;
	}

	public Long getPostParent() {
		return postParent;
	}

	public void setPostParent(Long postParent) {
		this.postParent = postParent;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostMimeType() {
		return postMimeType;
	}

	public void setPostMimeType(String postMimeType) {
		this.postMimeType = postMimeType;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "WpPosts [id=" + id + ", postAuthor=" + postAuthor + ", postDate=" + postDate + ", postDateGmt="
				+ postDateGmt + ", postContent=" + postContent + ", postTitle=" + postTitle + ", postExcerpt="
				+ postExcerpt + ", postStatus=" + postStatus + ", commentStatus=" + commentStatus + ", pingStatus="
				+ pingStatus + ", postPassword=" + postPassword + ", postName=" + postName + ", toPing=" + toPing
				+ ", pinged=" + pinged + ", postModified=" + postModified + ", postModifiedGmt=" + postModifiedGmt
				+ ", postContentFiltered=" + postContentFiltered + ", postParent=" + postParent + ", guid=" + guid
				+ ", menuOrder=" + menuOrder + ", postType=" + postType + ", postMimeType=" + postMimeType
				+ ", commentCount=" + commentCount + "]";
	}

	
}
