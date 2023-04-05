package org.example.Util;


import org.example.Model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HiberanteUtil {
	static SessionFactory factory = null;
	private static void Helper() {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Account.class)
				.addAnnotatedClass(Admin.class)
				.addAnnotatedClass(StudentBook.class)
				.addAnnotatedClass(Librarian.class)
				.addAnnotatedClass(Book.class);
				
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static SessionFactory getFactory() {
		Helper();
		return factory;
	}
	
}
