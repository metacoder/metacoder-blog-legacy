package de.metacoder.blog.persistence.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de">becker@jubeco.de</a>
 */
@Entity
public class BlogEntry extends AbstractEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@NonVisual
	private Long id;
	private String title;

	@Lob
	private String content;

	@ManyToMany
	private Set<User> authors = new HashSet<User>();
	
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

	public Set<User> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<User> authors) {
		this.authors = authors;
	}

}
