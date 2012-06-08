package de.metacoder.blog.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class SchemaExporter {
	
	public static void main(String... args) {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		
		for(Class<?> clazz : getEntitiesFromPackage("de.metacoder.blog.persistence.entities")){
			System.out.println("adding " + clazz);
			configuration.addAnnotatedClass(clazz);
		}
		
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setOutputFile("src/main/resources/metacoder-create-h2.sql");
		schemaExport.create(true, false);
	}

	protected static Collection<Class<?>> getEntitiesFromPackage(String packageName) {
		Collection<Class<?>> foundEntities = new ArrayList<Class<?>>();
		ClassPathScanningCandidateComponentProvider pathScanner = new ClassPathScanningCandidateComponentProvider(false);
		pathScanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for(BeanDefinition beanDefinition : pathScanner.findCandidateComponents(packageName)){
			try {
				foundEntities.add(Class.forName(beanDefinition.getBeanClassName()));
			} catch (ClassNotFoundException e) { 
				// shoud never happen here so wrap it in a runtime exception to avoid poisoning the methods signature here
				throw new RuntimeException(e);
			}
		}
		return foundEntities;
	}
}
