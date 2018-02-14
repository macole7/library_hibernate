package com.hibernate.library.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issue")
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "issue_id")
    private Integer issueId;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @Column(name = "issue_number")
    private Integer issueNumber;

    @Column(name = "book_id")
    private Integer bookId;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private Set<Publisher> publishers = new HashSet<>();

    public Issue(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Issue() {
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }
}
