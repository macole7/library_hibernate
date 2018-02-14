package com.hibernate.library.query;

import com.hibernate.library.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FindBookByName {
    public static void main(String[] args) {
        findByBookName("Ogniem i mieczem").stream().map(Book::getTitle).forEach(System.out::println);
    }

    public static List<Book> findByBookName(String name) {
        List<Book> booksByName = new ArrayList<>();
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                Query q = session.createQuery("From Book where book_name = " + name);
                booksByName = q.getResultList();
            }
        }
        return booksByName;
    }
}
