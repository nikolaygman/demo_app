package com.web.app.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.Book;
import com.web.app.model.Order;
import com.web.app.service.BookService;
import com.web.app.service.OrderService;
import com.web.app.service.UserService;

@Controller
public class RootController {

	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(@RequestParam(required = false, name = "search") String searchQuery,
			@RequestParam(required = false, name = "selector") String selector, HttpSession httpSession) {
		String session_id = httpSession.getId();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("session", session_id);
		modelAndView.setViewName("main");
		Order order = null;
		if (userService.currentUser() != null) {
			order = orderService.getLastOrderRelatedToUser(userService.currentUser().getId());
		} else {
			order = orderService.getLastOrderRelatedToSession(session_id);
		}
		modelAndView.addObject("order", order);
		ArrayList<Book> allBooks = null;
		/*
		 * Search
		 */
		if (searchQuery == null || searchQuery.isEmpty()) {
			modelAndView.addObject("allBooks", bookService.allBooks());
		} else {
			modelAndView.addObject("searchQuery", searchQuery);
			switch (selector) {

			case "byTitle":
				allBooks = bookService.findByTitle(searchQuery);
				modelAndView.addObject("allBooks", allBooks);
				break;
			case "byGenre":
				allBooks = bookService.findByGenre(searchQuery);
				modelAndView.addObject("allBooks", allBooks);
				break;
			case "byAuthor":
				allBooks = bookService.findByAuthor(searchQuery);
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
}
