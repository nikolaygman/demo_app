package com.web.app.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.web.app.model.Order;

@Service
public class OrderService {

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Set<Order> getOrdersRelatedToSession(String sessionId) {
		Session session = sessionFactory.openSession();
		Set<Order> foundedOrders = null;
		try {
			foundedOrders = new LinkedHashSet<Order>(
					session.createQuery(String.format("from Order where sessionId = '%s' order by created_at desc", sessionId)).list());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedOrders;
	}

	public Set<Order> getOrdersRelatedToUser(int user_id) {
		Session session = sessionFactory.openSession();
		Set<Order> foundedOrders = null;
		try {
			foundedOrders = new LinkedHashSet<Order>(
					session.createQuery(String.format("from Order where user_id = '%s' order by created_at desc", user_id)).list());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedOrders;
	}

	public void saveOrder(Order order) {

		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(order);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Order getLastOrderRelatedToUser(int user_id) {
		Session session = sessionFactory.openSession();
		Order order = null;
		try {
			order = (Order) session
			.createQuery((String.format("from Order where user_id = %s order by createdAt desc", user_id)))
			.list()
			.get(0);
		} catch (Exception e) {
			
		} finally {
			session.close();
		}
		return order;
	}
	public Order getLastOrderRelatedToSession(String session_id) {
		Session session = sessionFactory.openSession();
		Order order = null;
		try {
			order = (Order) session
			.createQuery((String.format("from Order where session_id = '%s' order by createdAt desc", session_id)))
			.list()
			.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}

}
