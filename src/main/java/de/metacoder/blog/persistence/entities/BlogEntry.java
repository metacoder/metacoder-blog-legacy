package de.metacoder.blog.persistence.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de">becker@jubeco.de</a>
 */
@Entity
public class BlogEntry extends AbstractEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private String title;

	@Lob
	private String content;

	@ManyToOne
	private UserBO author;
	
	@OneToMany(orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	@OrderBy("creationDate ASC")
	private Set<BlogEntryComment> comments = new HashSet<BlogEntryComment>();
	
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

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public UserBO getAuthor() {
		return author;
	}

	public void setAuthor(UserBO author) {
		this.author = author;
	}

	public Set<BlogEntryComment> getComments() {
		return comments;
	}

	public void setComments(Set<BlogEntryComment> comments) {
		this.comments = comments;
	}
	
}
