package com.web.app.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.app.model.Author;
import com.web.app.model.Book;
import com.web.app.model.Genre;

@Service
public class BookService {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void addBook(Book book) {
		Session session = sessionFactory.openSession();
		if (findByTitle((book.getTitle())) == null) {
			try {
				session.beginTransaction();
				Set<Genre> genres = new LinkedHashSet<>();
				for (Genre genre : book.getGenres()) {
					Genre dbGenre = new GenreService().findByName(genre.getName());
					if (dbGenre != null) {
						genres.add(dbGenre);
					} else {
						genres.add(genre);
					}
				}
				book.setGenres(genres);

				Set<Author> authors = new LinkedHashSet<>();
				for (Author author : book.getAuthors()) {
					Author dbAuthor = new AuthorService().findByName(author.getName());
					if (dbAuthor != null) {
						authors.add(dbAuthor);
					} else {
						authors.add(author);
					}
				}

				book.setAuthors(authors);

				session.save(book);
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			} finally {
				session.getTransaction().commit();
				session.close();
			}
		}
	}

/*	public List<Book> allBooks(int booksPerPageCount) {
		Session session = sessionFactory.openSession();
		List<Book> allBooks = new ArrayList<>();
		try {
			Query query = session.createQuery("from Book");
			query.setMaxResults(booksPerPageCount);
			allBooks = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return allBooks;
	}*/

	public List<Book> allBooks (int pagePosition,int booksPerPageCount) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			Query query = session.createQuery("from Book");
			query.setFirstResult((pagePosition-1) * (booksPerPageCount));
			query.setMaxResults(booksPerPageCount);
			foundedBooks = (ArrayList<Book>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
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

	public Book findByTitle(String bookTitle) {
		Session session = sessionFactory.openSession();
		Book foundedBook = null;
		try {
			Query query = session.createQuery("from Book where title = :bookTitle");
			query.setParameter("bookTitle", bookTitle);
			List bookList = query.list();
			if (!bookList.isEmpty()) {
				foundedBook = (Book) bookList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBook;
	}

	public ArrayList<Book> searchByTitle(String bookTitle) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			Query query = session.createQuery("from Book where title LIKE :bookTitle");
			query.setParameter("bookTitle", bookTitle + "%");
			foundedBooks = (ArrayList<Book>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}

	public ArrayList<Book> searchByGenre(String genre) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			Query query = session.createQuery("select distinct b  from Book b join b.genres a where a.name LIKE :genre");
			query.setParameter("genre", genre + "%");
			foundedBooks = (ArrayList<Book>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}

	public ArrayList<Book> searchByAuthor(String author) {
		Session session = sessionFactory.openSession();
		ArrayList<Book> foundedBooks = new ArrayList<Book>();
		try {
			Query query = session.createQuery("select distinct b  from Book b join b.authors a where a.name LIKE :author");
			query.setParameter("author", author + "%");
			foundedBooks = (ArrayList<Book>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedBooks;
	}
	public int getBooksLastPage(int booksPerPageCount) {
		Session session = sessionFactory.openSession();
		Long totalBookCount = 0L ;
		try {
			Query query = session.createQuery("Select count (b.id) from Book b");
			totalBookCount  = (Long) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return (int) (Math.ceil(totalBookCount / booksPerPageCount));
	}
}
