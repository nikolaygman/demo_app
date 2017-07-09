package com.web.app.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.web.app.model.Author;
import com.web.app.model.Genre;
import com.web.app.service.HibernateUtil;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.Book;
import com.web.app.model.Order;
import com.web.app.service.BookService;
import com.web.app.service.OrderService;
import com.web.app.service.UserService;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(@RequestParam(required = false, defaultValue = "1") int page,
							 @RequestParam(required = false, name = "search") String searchQuery,
							 @RequestParam(required = false, name = "selector") String selector, HttpSession httpSession, ModelAndView modelAndView) {
		int booksPerPageCount = 5;
		int lastPage = bookService.getBooksLastPage(booksPerPageCount);
		if (page <= 0 || page > lastPage) {
			modelAndView.setViewName("fail_search");
			return modelAndView;
		}

		String session_id = httpSession.getId();
		modelAndView.addObject("currentPagePosition", page);
		modelAndView.addObject("lastPagePosition", bookService.getBooksLastPage(booksPerPageCount));
		modelAndView.addObject("session", session_id);
		modelAndView.setViewName("main");
		Order order = null;
		if (userService.currentUser() != null) {
			order = orderService.getLastOrderRelatedToUser(userService.currentUser().getId());
		} else {
			order = orderService.getLastOrderRelatedToSession(session_id);
		}
		if (order != null) {
			modelAndView.addObject("order", order);
		}
		ArrayList<Book> allBooks = null;
		/*
		 * Search
		 */
		if (searchQuery == null || searchQuery.isEmpty()) {
			modelAndView.addObject("allBooks", bookService.allBooks(page, booksPerPageCount));
		} else {
			modelAndView.addObject("searchQuery", searchQuery);
			switch (selector) {

				case "byTitle":
					allBooks = bookService.searchByTitle(searchQuery);
					modelAndView.addObject("allBooks", allBooks);
					break;
				case "byGenre":
					allBooks = bookService.searchByGenre(searchQuery);
					modelAndView.addObject("allBooks", allBooks);
					break;
				case "byAuthor":
					allBooks = bookService.searchByAuthor(searchQuery);
					modelAndView.addObject("allBooks", allBooks);
					break;
				default:
					break;
			}
			if (allBooks.isEmpty()) {
				modelAndView.setViewName("fail_search");
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/populate", method = RequestMethod.GET)
	public ModelAndView populate(ModelAndView modelAndView) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Document doc = Jsoup.connect("http://www.goodreads.com/list/show/23772.Oxford_World_s_Classics").get();
			Elements elements = doc.select("a.bookTitle");
			for (Element element : elements) {

				doc = Jsoup.connect(element.attr("abs:href")).get();

				String bookTitle = doc.select("#bookTitle").text();

				String bookDescription = doc.select("#description span[style='display:none']").text();

				String book_img = doc.select("#coverImage").attr("src");

				Author author = new Author();
				author.setName(doc.select(".authorName:first-child span").text());
				LinkedHashSet<Author> authors = new LinkedHashSet<Author>();
				authors.add(author);

				LinkedHashSet<Genre> genres = new LinkedHashSet<>();
				Elements genresElement = doc.select(".rightContainer .bigBoxBody .elementList .left");
				for (Element genreElement : genresElement) {
					Genre genre = new Genre();
					genre.setName(genreElement.text());
					genres.add(genre);
				}

				double min = 19.99;
				double max = 99.99;
				Random r = new Random();
				double randomNumber = min + (max - min) * r.nextDouble();
				randomNumber = Double.parseDouble(new DecimalFormat("#0.00").format(randomNumber).replace(",", "."));

				Book book = new Book(bookTitle, bookDescription, book_img, randomNumber, genres, authors);
				BookService bookService = new BookService();
				bookService.addBook(book);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		modelAndView.setViewName("main");
		return modelAndView;
	}
	@RequestMapping(value = "/redirect_home",method = RequestMethod.GET)
	public String redirect_home () {
		return "redirect:/";
	}
}
