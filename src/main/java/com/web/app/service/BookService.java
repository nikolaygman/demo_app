package com.web.app.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.web.app.model.Book;

@Service
public class BookService {

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public List<Book> allBooks() {
		Session session = sessionFactory.openSession();
		List<Book> allBooks = new ArrayList<>();
		try {
			allBooks = session.createQuery("from Book").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return allBooks;
	}

	public Book findById(int id) {
		Session session = sessionFactory.openSession();
		Book book = null;
		try {
			book = session.get(Book.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return book;
	}

	public ArrayList<Book> findByTitle(String bookTitle) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			foundedBooks = new ArrayList<Book>(session
					.createQuery(String.format("from Book where title LIKE '%s'", "%" + bookTitle + "%")).list());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}
	public ArrayList<Book> findByGenre(String genre) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			foundedBooks = new ArrayList<Book>(session
					.createQuery(String.format("select distinct b from Book b join b.genres g where g.name LIKE '%s'", "%" + genre + "%")).list());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}
	public ArrayList<Book> findByAuthor(String author) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			foundedBooks = new ArrayList<Book>(session
					.createQuery(String.format("select distinct b  from Book b join b.authors a where a.author_name LIKE '%s'", "%" + author + "%")).list());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}
}
