package com.example.webcomic.project.demo.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "comic_id", nullable = false)
	private Comic comic;
	private String content;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
	private Chapter chapter;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	private Calendar date_created;
	
	public Comment() {
		super();
	}

	

	public Comment(long id, Comic comic, String content, Chapter chapter, User user, Calendar date_created) {
		super();
		this.id = id;
		this.comic = comic;
		this.content = content;
		this.chapter = chapter;
		this.user = user;
		this.date_created = date_created;
	}

	

	public Comment(long id, String content, Calendar date_created) {
		super();
		this.id = id;
		this.content = content;
		this.date_created = date_created;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public Comic getComic() {
//		return comic;
//	}
//
	public void setComic(Comic comic) {
		this.comic = comic;
	}
//
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public Calendar getDate_created() {
		return date_created;
	}



	public void setDate_created(Calendar date_created) {
		this.date_created = date_created;
	}

//	public Chapter getChapter() {
//		return chapter;
//	}
//
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
//
//	public User getUser() {
//		return user;
//	}
//
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
