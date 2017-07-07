package com.web.app.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.web.app.model.Author;

import java.util.ArrayList;

@Service
public class AuthorService {

	private SessionFactory  sessionFactory = HibernateUtil.getSessionFactory();
	Author findByName(String name) {
		Session session = sessionFactory.openSession();
		Author author = null;
		try {
			ArrayList<Author> authorsList = (ArrayList<Author>) session.createQuery(String.format("from Author where name = '%s'",name)).list();
			if(!authorsList.isEmpty()) {
				author = authorsList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return author;
	}
}
