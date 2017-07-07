package com.web.app.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.web.app.model.Genre;

import java.util.ArrayList;

@Service
public class GenreService {

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	public Genre findByName(String name) {
		Session session = sessionFactory.openSession();
		Genre genre = null;
		try {
			ArrayList<Genre> genresList = (ArrayList<Genre>) session.createQuery(String.format("from Genre where name = '%s'",name)).list();
			if (!genresList.isEmpty()) {
				genre = genresList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return genre;
	}
}
