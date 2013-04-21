package de.metacoder.blog.persistence.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AbstractEntity {
	
	private Date creationDate;

	private Date lastUpdateDate;

	@PrePersist
	protected void generateCreationDate() {
		creationDate = new Date();
	}

	@PreUpdate
	protected void updateLastUpdateDate() {
		lastUpdateDate = new Date();
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
}
