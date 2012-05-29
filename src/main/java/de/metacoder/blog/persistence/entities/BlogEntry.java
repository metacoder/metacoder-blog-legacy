package de.metacoder.blog.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 * 
 */
@Entity
public class BlogEntry {

	public BlogEntry(String title, Date creationDate, String content) {
		this.title = title;
		this.creationDate = creationDate;
		this.content = content;
	}
	
	public BlogEntry(){
		// for hibernate 
	}
	
	private Long id;
	private String title;
	private Date creationDate;
	private String content;
	
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@NonVisual
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "BlogEntry [id=" + id + ", title=" + title + ", creationDate="
				+ creationDate + ", content=" + content + "]";
	}
	
	
}
