package com.hibernate.library.prefill;

import com.hibernate.library.model.Author;
import com.hibernate.library.model.Book;
import com.hibernate.library.model.Issue;
import com.hibernate.library.model.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class PrefillDB {

    private static List<Author> authors = Arrays.asList(new Author("Henryk", "Sienkiewicz"), new Author("Joanne", "Rowling"),
            new Author("Joshua", "Bloch"), new Author("Boleslaw", "Prus"));

    private static List<Publisher> publishers = Arrays.asList(new Publisher("PWN", "Warszawa"),
            new Publisher("Helion", "Wroclaw"), new Publisher("Pascal", "Poznan"));

    private static List<Book> books = Arrays.asList(new Book("Ogniem i Mieczem"), new Book("Quo vadis"), new Book("Krzyzacy"),
            new Book("Potop"), new Book("Harry Potter"), new Book("Effective Java"), new Book("Lalka"), new Book("Antek"));

    private static List<Issue> issues = Arrays.asList(new Issue(1), new Issue(2), new Issue(3));

    public static void main(String[] args) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                try {
                    transaction = session.beginTransaction();
                    authors.stream().forEach(session::save);
                    publishers.stream().forEach(session::save);
                    books.stream().forEach(session::save);
                    issues.stream().forEach(session::save);
                    addBooksToAuthor().stream().forEach(session::save);
                    addPublisherToIssue().stream().forEach(session::save);
                    addIssueToBook().stream().forEach(session::save);
                    transaction.commit();

                } catch (RuntimeException e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.getStackTrace();
                }
            }
        }
    }

    private static List<Author> addBooksToAuthor() {
        authors.get(0).getBooks().add(books.get(0));
        authors.get(0).getBooks().add(books.get(1));
        authors.get(0).getBooks().add(books.get(2));
        authors.get(0).getBooks().add(books.get(3));
        authors.get(1).getBooks().add(books.get(4));
        authors.get(2).getBooks().add(books.get(5));
        authors.get(3).getBooks().add(books.get(6));
        authors.get(3).getBooks().add(books.get(7));
        return authors;
    }

    private static List<Book> addIssueToBook() {
        books.get(0).setIssue(issues.get(0));
        books.get(1).setIssue(issues.get(1));
        books.get(2).setIssue(issues.get(2));
        books.get(3).setIssue(issues.get(0));
        books.get(4).setIssue(issues.get(1));
        books.get(5).setIssue(issues.get(2));
        books.get(6).setIssue(issues.get(0));
        books.get(7).setIssue(issues.get(1));
        return books;

    }

    private static List<Issue> addPublisherToIssue() {
        issues.get(0).getPublishers().add(publishers.get(0));
        issues.get(1).getPublishers().add(publishers.get(1));
        issues.get(2).getPublishers().add(publishers.get(2));
        return issues;
    }
}
