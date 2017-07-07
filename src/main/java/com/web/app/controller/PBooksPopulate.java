package com.web.app.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.web.app.model.Author;
import com.web.app.model.Book;
import com.web.app.model.Genre;
import com.web.app.service.BookService;
import com.web.app.service.HibernateUtil;

public class PBooksPopulate {

	public static void main(String[] args) throws InterruptedException {

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
				randomNumber = Double.valueOf(new DecimalFormat("#0.00").format(randomNumber));

				Book book = new Book(bookTitle,bookDescription,book_img,randomNumber,genres,authors);
				BookService bookService = new BookService();
				bookService.addBook(book);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

}
