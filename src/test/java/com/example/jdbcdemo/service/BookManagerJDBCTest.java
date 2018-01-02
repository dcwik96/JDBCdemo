package com.example.jdbcdemo.service;

import com.example.jdbcdemo.domain.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookManagerJDBCTest {

    private final static String TITLE_1 = "Krzyzacy";
    private final static String AUTHOR_1 = "Heniu Siena";
    private final static double PRICE_1 = 20.99;

    private final static String TITLE_2 = "QuoVadis";
    private final static String AUTHOR_2 = "Heniu Siena";
    private final static double PRICE_2 = 10.99;

    private final static String TITLE_3 = "Niebieska Mila";
    private final static String AUTHOR_3 = "Stephen Queen";
    private final static double PRICE_3 = 99.99;

    Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
    Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);
    Book book3 = new Book(TITLE_3, AUTHOR_3, PRICE_3);

    private BookManagerJDBC bookManager = new BookManagerJDBC();


    @Test
    public void checkConnection() {
        assertNotNull(bookManager.getConnection());
    }


    @Before
    public void checkDeletingAllBooks() {
        bookManager.deleteAllBooks();

        List<Book> books = bookManager.getAllBooks();
        assertEquals(true, books.isEmpty());
    }


    @Test
    public void checkAdding() {

        assertEquals(1, bookManager.addBook(book));

        List<Book> books = bookManager.getAllBooks();
        Book bookRetrieved = books.get(0);

        assertEquals(1, bookManager.getAllBooks().size());

        assertEquals(book.getTitle(), bookRetrieved.getTitle());
        assertEquals(book.getAuthor(), bookRetrieved.getAuthor());
        assertEquals(book.getPrice(), bookRetrieved.getPrice(), 0.01);
    }

    @Test
    public void checkAddAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);

        bookManager.addAllBooks(books);
        int size = bookManager.getAllBooks().size();
        assertThat(size, either(is(3)).or(is(0)));
    }

    @Test
    public void checkFindById() {

        bookManager.addBook(book);

        List<Book> books = bookManager.getAllBooks();
        Book tmp = books.get(0);

        Book bookRetrieved = bookManager.findById(tmp.getId());

        assertEquals(book.getTitle(), bookRetrieved.getTitle());
        assertEquals(book.getAuthor(), bookRetrieved.getAuthor());
        assertEquals(book.getPrice(), bookRetrieved.getPrice(), 0.01);
    }

    @Test
    public void checkFindByTitle() {

        bookManager.addBook(book);
        bookManager.addBook(book2);

        List<Book> books = bookManager.findByTitle(book2.getTitle());
        assertEquals(1, books.size());
        Book bookRetrived = books.get(0);

        assertEquals(book2.getTitle(), bookRetrived.getTitle());
    }

    @Test
    public void checkFindByAuthor() {

        bookManager.addBook(book);
        bookManager.addBook(book2);

        List<Book> books = bookManager.findByAuthor(book2.getAuthor());
        assertEquals(2, books.size());
        Book bookRetrived = books.get(0);

        assertEquals(book2.getAuthor(), bookRetrived.getAuthor());

    }

    @Test
    public void checkFindByPrice() {

        bookManager.addBook(book);
        bookManager.addBook(book2);

        List<Book> books = bookManager.findByPrice(book2.getPrice());
        Book bookRetrived = books.get(0);

        assertEquals(book2.getPrice(), bookRetrived.getPrice(), 0.01);
    }

    @Test
    public void checkDeletingSingleBook() {
        bookManager.addBook(book);
        bookManager.addBook(book2);

        List<Book> books = bookManager.getAllBooks();
        Book tmp = books.get(1);

        assertEquals(1, bookManager.deleteBookById(tmp.getId()));

        books = bookManager.getAllBooks();
        Book bookRetrived = books.get(0);

        assertEquals(1, books.size());

        assertEquals(book.getTitle(), bookRetrived.getTitle());
        assertEquals(book.getAuthor(), bookRetrived.getAuthor());
        assertEquals(book.getPrice(), bookRetrived.getPrice(), 0.01);
    }

    @Test
    public void checkUpdateWholeBook() {
        bookManager.addBook(book);
        bookManager.addBook(book2);

        assertEquals(1, bookManager.updateWholeBook(book, TITLE_3, AUTHOR_3, PRICE_3));

        List<Book> books = bookManager.getAllBooks();
        Book bookRetrived = books.get(1);

        assertEquals(TITLE_3, bookRetrived.getTitle());
        assertEquals(AUTHOR_3, bookRetrived.getAuthor());
        assertEquals(PRICE_3, bookRetrived.getPrice(), 0.01);
    }

    @Test
    public void checkUpdateBookTitle() {

        bookManager.addBook(book);
        bookManager.addBook(book2);

        assertEquals(1, bookManager.updateBookTitle(book, TITLE_3));

        List<Book> books = bookManager.getAllBooks();
        Book bookRetrived = books.get(1);

        assertEquals(TITLE_3, bookRetrived.getTitle());
    }

    @Test
    public void checkUpdateBookAuthor() {

        bookManager.addBook(book);
        bookManager.addBook(book2);
        assertEquals(1, bookManager.updateBookAuthor(book, AUTHOR_3));

        List<Book> books = bookManager.getAllBooks();
        Book bookRetrived = books.get(1);

        assertEquals(AUTHOR_3, bookRetrived.getAuthor());
    }

    @Test
    public void checkUpdateBookPrice() {

        bookManager.addBook(book);
        bookManager.addBook(book2);
        assertEquals(1, bookManager.updateBookPrice(book, PRICE_3));

        List<Book> books = bookManager.getAllBooks();
        Book bookRetrived = books.get(1);

        assertEquals(PRICE_3, bookRetrived.getPrice(), 0.01);
    }

    @Test
    public void checkDeleteSelectedBooks() {
        bookManager.addBook(book);
        bookManager.addBook(book2);
        bookManager.addBook(book3);

        List<Book> books = bookManager.getAllBooks();
        List<Long> ids = new ArrayList<>();
        ids.add(books.get(0).getId());
        ids.add(books.get(1).getId() + 100);

        assertThat(bookManager.deleteSelectedBooks(ids), either(is(3)).or(is(1)));
    }

    @Test
    public void checkUpdateSelectedBooks() {
        List<Book> books;
        List<Book> temp = new ArrayList<>();

        bookManager.addBook(book);
        bookManager.addBook(book2);
        bookManager.addBook(book3);

        books = bookManager.getAllBooks();

        temp.add(books.get(0));
        temp.add(books.get(1));
        temp.get(0).setTitle("TEST");
//        temp.get(0).setId(100);
        temp.get(1).setTitle("ZMIANA");


        assertThat(bookManager.updateSelectedBooks(temp), either(is(2)).or(is(0)));
    }
}

