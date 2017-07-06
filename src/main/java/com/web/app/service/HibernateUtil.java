package com.web.app.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.web.app.model.Author;
import com.web.app.model.Authority;
import com.web.app.model.Book;
import com.web.app.model.Genre;
import com.web.app.model.Order;
import com.web.app.model.User;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static {
		String dbUrl;
		String name = "postgres";
		String password = "kolya2009";
		Configuration configuration = new Configuration();
		try {
			URI dbUri = new URI(System.getenv("DATABASE_URL"));
			dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			name = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
		} catch (NullPointerException | URISyntaxException e) {
			dbUrl = "jdbc:postgresql://localhost:5432/forum";
		}
		configuration.setProperty("hibernate.connection.username", name);
		configuration.setProperty("hibernate.connection.password", password);
		configuration.setProperty("hibernate.connection.url", dbUrl);
		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		configuration.addAnnotatedClass(Book.class);
		configuration.addAnnotatedClass(Genre.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Authority.class);
		configuration.addAnnotatedClass(Author.class);
		configuration.addAnnotatedClass(Order.class);
		sessionFactory = configuration.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
