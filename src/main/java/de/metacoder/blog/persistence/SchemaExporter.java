package de.metacoder.blog.persistence;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import de.metacoder.blog.persistence.entities.BlogEntry;
import de.metacoder.blog.persistence.entities.User;

public class SchemaExporter {
	public static void main(String... args){
		Configuration configuration = new Configuration();
		// doesn't work, dunno why yet
//		configuration.addPackage("de.metacoder.blog.persistence.entities");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		configuration.addAnnotatedClass(BlogEntry.class);
		configuration.addAnnotatedClass(User.class);
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setOutputFile("src/main/resources/metacoder-create-h2.sql");
		schemaExport.create(true, false);
	}
}
