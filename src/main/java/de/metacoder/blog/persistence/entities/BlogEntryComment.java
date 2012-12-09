package de.metacoder.blog.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class BlogEntryComment extends AbstractEntity {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@NonVisual
	private Long id;

	@Column(length=5000)
	private String content;
	
	@Column(length=100)
	private String authorName;
	
	@Column(length=100)
	private String authorEmail;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	
}
