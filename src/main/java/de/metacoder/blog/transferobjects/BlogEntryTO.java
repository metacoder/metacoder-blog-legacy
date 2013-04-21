package de.metacoder.blog.transferobjects;

import java.util.ArrayList;
import java.util.Date;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.BlogEntryComment;

public class BlogEntryTO {

	private String title;
	private String content;

	@NonVisual
	private String author;
	
	@NonVisual
	private Date creationDate;
	
	@NonVisual
	private Long id;
	
	@NonVisual
	private Date lastUpdated;
	
	private ArrayList<BlogEntryCommentTO> comments;

	@Inject // required for Tapestry (remove when external BO / TO mapper exists)
	public BlogEntryTO(){
		
	}
	
	public BlogEntryTO(BlogEntry blogEntry){ // TODO FIXME no dependency to entity in TO class!
		content = blogEntry.getContent();
		author = blogEntry.getAuthor().getName();
		creationDate = blogEntry.getCreationDate();
		id = blogEntry.getId();
		lastUpdated = blogEntry.getLastUpdateDate();
		title = blogEntry.getTitle();
		
		comments = new ArrayList<BlogEntryCommentTO>();
		
		for(BlogEntryComment comment : blogEntry.getComments()){
			comments.add(new BlogEntryCommentTO(comment));
		}
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setComments(ArrayList<BlogEntryCommentTO> comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Long getId() {
		return id;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public ArrayList<BlogEntryCommentTO> getComments() {
		return comments;
	}
}
