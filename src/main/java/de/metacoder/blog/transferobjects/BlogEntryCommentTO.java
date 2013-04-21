package de.metacoder.blog.transferobjects;

import java.util.Date;

import de.metacoder.blog.persistence.entities.BlogEntryComment;

public class BlogEntryCommentTO {

	private Date creationDate;
	private String authorname;
	private String content;
	private String authorEmail;

	public BlogEntryCommentTO(BlogEntryComment comment) {
		authorname = comment.getAuthorName();
		content = comment.getContent();
		creationDate = comment.getCreationDate();
		authorEmail = comment.getAuthorEmail();
	}

	public BlogEntryCommentTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAuthorEmail() {
		return authorEmail;
	};
	
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public String getAuthorname() {
		return authorname;
	}
	
	public String getContent() {
		return content;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BlogEntryComment toBlogEntryComment() {
		BlogEntryComment comment = new BlogEntryComment();
		comment.setAuthorEmail(authorEmail);
		comment.setAuthorName(authorname);
		comment.setContent(content);
		comment.setCreationDate(creationDate);
		return comment;
	}
	
	

}
