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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefillDB {

    private static List<Author> authors = Arrays.asList(new Author("Henryk", "Sienkiewicz"), new Author("Joanne", "Rowling"),
            new Author("Joshua", "Bloch"), new Author("Boleslaw", "Prus"));

    private static List<Publisher> publishers = Arrays.asList(new Publisher("PWN", "Warszawa"),
            new Publisher("Helion", "Wroclaw"), new Publisher("Pascal", "Poznan"));

    private static List<Book> books = Arrays.asList(new Book("Ogniem i Mieczem"), new Book("Quo vadis"), new Book("Krzyzacy"),
            new Book("Potop"), new Book("Harry Potter"), new Book("Effective Java"), new Book("Lalka"), new Book("Antek"));


    public static void main(String[] args) {
        Map<Author, List<Book>> namesOfBooks = createNamesOfBooks();
        Map<Publisher, List<Integer>> issues = createIssue();
        Map<Issue, List<Book>> dependenceBetweenBookAndIssue = createDependenceBetweenBookAndIssue();

        Transaction transaction = null;
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                try {
                    transaction = session.beginTransaction();
                    session.save(authors);
                    session.save(publishers);
                    session.save(books);
                    transaction.commit();

                } catch (RuntimeException e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                }
            }
        }
    }

    private static Map<Author, List<Book>> createNamesOfBooks() {
        Map<Author, List<Book>> result = new HashMap<>();
        result.put(authors.get(0), Arrays.asList(books.get(0), books.get(1), books.get(2), books.get(3)));
        result.put(authors.get(1), Arrays.asList(books.get(4)));
        result.put(authors.get(2), Arrays.asList(books.get(5)));
        result.put(authors.get(3), Arrays.asList(books.get(6), books.get(7)));
        return result;
    }

    private static Map<Publisher, List<Integer>> createIssue() {
        Map<Publisher, List<Integer>> issues = new HashMap<>();
        issues.put(publishers.get(0), Arrays.asList(1, 2, 3));
        issues.put(publishers.get(1), Arrays.asList(2, 3, 6));
        issues.put(publishers.get(2), Arrays.asList(4, 6, 7));
        return issues;
    }

    private static Map<Issue, List<Book>> createDependenceBetweenBookAndIssue() {
        Map<Issue, List<Book>> issues = new HashMap<>();
        issues.put(new Issue(1), Arrays.asList(books.get(0), books.get(5), books.get(3)));
        issues.put(new Issue(2), Arrays.asList(books.get(1), books.get(2), books.get(3)));
        issues.put(new Issue(3), Arrays.asList(books.get(0), books.get(7), books.get(4), books.get(6)));
        return issues;
    }
}
