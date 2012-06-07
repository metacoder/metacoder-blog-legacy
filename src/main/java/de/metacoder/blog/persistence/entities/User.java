package de.metacoder.blog.persistence.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

/**
 * @author Benjamin Neff <a href="mailto:benjamin@coding4coffee.ch">benjamin@coding4coffee.ch</a>
 * @author Felix Becker <a href="mailto:becker@jubeco.de>becker@jubeco.de</a>
 */
@Entity
public class User {

	@Id
	private String name;

	// TODO: hash and salt!
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<String>();

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(final Set<String> roles) {
		this.roles = roles;
	}
}
