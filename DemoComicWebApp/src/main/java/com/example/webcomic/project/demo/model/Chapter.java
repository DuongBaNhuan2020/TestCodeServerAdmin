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

import org.springframework.beans.factory.annotation.Autowired;

import com.example.webcomic.project.demo.repository.ComicRepository;
@Entity
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "comic_id", nullable = false)
	private Comic comic;
	private String name;
	private Calendar date_created;
	private String content;
	@OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Comment> listComment;
	
	public Chapter() {
		super();
	}



	public Chapter(long id, Comic comic, String name, Calendar date_created, String content,
			List<Comment> listComment) {
		super();
		this.id = id;
		this.comic = comic;
		this.name = name;
		this.date_created = date_created;
		this.content = content;
		this.listComment = listComment;
	}

	

	public Chapter(long id, String name, Calendar date_created, String content, List<Comment> listComment) {
		super();
		this.id = id;
		this.name = name;
		this.date_created = date_created;
		this.content = content;
		this.listComment = listComment;
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate_created() {
		return date_created;
	}

	public void setDate_created(Calendar date_created) {
		this.date_created = date_created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getListComment() {
		return listComment;
	}

	public void setListComment(List<Comment> listComment) {
		this.listComment = listComment;
	}
	
	
}
