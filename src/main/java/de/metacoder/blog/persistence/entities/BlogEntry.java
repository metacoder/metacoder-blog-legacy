package de.metacoder.blog.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de">becker@jubeco.de</a>
 */
@Entity
public class BlogEntry {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@NonVisual
	private Long id;
	private String title;

	@NonVisual
	private Date creationDate;

	@NonVisual
	private Date lastUpdateDate;

	@PrePersist
	protected void generateCreationDate() {
		creationDate = new Date();
	}

	@PreUpdate
	protected void updateLastUpdateDate() {
		lastUpdateDate = new Date();
	}

	@Lob
	private String content;
	private String authorName;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(final Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "BlogEntry [id=" + id + ", title=" + title + ", creationDate="
				+ creationDate + ", lastUpdateDate=" + lastUpdateDate
				+ ", content=" + content + ", authorName=" + authorName + "]";
	}
}
