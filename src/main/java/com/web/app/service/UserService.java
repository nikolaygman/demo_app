package com.web.app.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.app.model.User;

@Service
public class UserService implements UserDetailsService {

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void createUser(User user) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			user = (User) session.createQuery(String.format("from User where username = '%s'", username)).list().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException(e.getMessage());
		} finally {
			session.close();
		}
		return user;
	}

	public boolean isAnonimous() {
		return (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ? true
				: false;
	}

	public User currentUser() {
		User user;
		try {
			user = (User) loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (UsernameNotFoundException e) {
			return null;
		}
		return user;
	}
}
