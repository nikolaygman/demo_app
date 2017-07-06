package com.web.app.controller;

import java.text.DecimalFormat;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/make_order", method = RequestMethod.POST, produces = { "text/html" })
	public @ResponseBody String make_order(RedirectView redirectView, ModelMap map, int book_id, @Valid Order order,
			BindingResult bindingResult, HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			String errorList = bindingResult.getAllErrors().get(0).getDefaultMessage();
			return errorList;
		} else {
			order.setSessionId(httpSession.getId());
			order.setBook(bookService.findById(book_id));
			if (!userService.isAnonimous()) {
				order.setUser(userService.currentUser());
			}
			orderService.saveOrder(order);
		}
		return "no_errors";
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ModelAndView my_orders(ModelAndView modelAndView, HttpSession session) {
		LinkedHashSet<Order> orders;
		if (userService.currentUser() == null) {
			orders = (LinkedHashSet<Order>) orderService.getOrdersRelatedToSession(session.getId());
		} else {
			orders = (LinkedHashSet<Order>) orderService.getOrdersRelatedToUser(userService.currentUser().getId());
		}
		if(orders.isEmpty()) {
			modelAndView.setViewName("empty_orders");
		} else {
			modelAndView.addObject("orders", orders);
			modelAndView.setViewName("orders");
		}
		return modelAndView;
	}

	@RequestMapping(value = "updateTotal", method = RequestMethod.GET, produces = { "tex/html; UTF-8" })
	public @ResponseBody String updateTotal(@RequestParam int quantity, int book_id) {
		Book book = bookService.findById(book_id);
		return new DecimalFormat("#0.00").format(book.getPrice() * quantity);
	}
}
