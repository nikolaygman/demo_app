package com.web.app.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.web.app.model.Book;
import com.web.app.model.Order;
import com.web.app.service.BookService;
import com.web.app.service.OrderService;
import com.web.app.service.UserService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/make_order", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody
	HashMap<String, String> make_order(String book_title, @Valid Order order,
									   BindingResult bindingResult, HttpSession httpSession) {
		HashMap<String, String> response = new HashMap<>();
		if (bindingResult.hasErrors()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				response.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return response;
		} else {
			order.setBook(bookService.findByTitle(book_title));
			order.setSessionId(httpSession.getId());
			if (userService.currentUser() != null) {
				order.setUser(userService.currentUser());
			}
			orderService.saveOrder(order);
			response.put("success","");
			return response;
		}
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ModelAndView my_orders(ModelAndView modelAndView, HttpSession session) {
		ArrayList<Order> orders;
		if (userService.currentUser() == null) {
			orders = (ArrayList<Order>) orderService.getOrdersRelatedToSession(session.getId());
		} else {
			orders = (ArrayList<Order>) orderService.getOrdersRelatedToUser(userService.currentUser().getId());
		}
		if (orders.isEmpty()) {
			modelAndView.setViewName("empty_orders");
		} else {
			modelAndView.addObject("orders", orders);
			modelAndView.setViewName("orders");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/updateTotal", method = RequestMethod.GET, produces = {"tex/html; UTF-8"})
	public @ResponseBody
	String updateTotal(@RequestParam int quantity, String bookTitle) {
		Book book = bookService.findByTitle(bookTitle);
		return new DecimalFormat("#0.00").format(book.getPrice() * quantity);
	}
}
