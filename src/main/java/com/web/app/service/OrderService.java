package com.web.app.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.web.app.model.Order;


@Service
public class OrderService {

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public ArrayList<Order> getOrdersRelatedToSession(String sessionId) {
		Session session = sessionFactory.openSession();
		ArrayList<Order> foundedOrders = null;
		try {
			Query query = session.createQuery("from Order where sessionId = :sessionId order by created_at desc");
			query.setParameter("sessionId",sessionId);
			foundedOrders = (ArrayList<Order>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return foundedOrders;
	}

	public ArrayList<Order> getOrdersRelatedToUser(int user_id) {
		Session session = sessionFactory.openSession();
		ArrayList<Order> foundedOrders = null;
		try {
			Query query = session.createQuery("from Order where sessionId = :user_id order by created_at desc");
			query.setParameter("user_id",user_id);
			foundedOrders = (ArrayList<Order>) query.list();
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
			Query query = session.createQuery("from Order where user_id = :user_id");
			query.setParameter("user_id", user_id);
			List<Order> orderList = query.list();
			if (!orderList.isEmpty()) {
				order = orderList.get(0);
			}
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
			Query query = session.createQuery("from Order where session_id = :session_id " +
					"order by createdAt desc");
			query.setParameter("session_id", session_id);
			List<Order> orderList = query.list();
			if (!orderList.isEmpty()) {
				order = orderList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}

}
