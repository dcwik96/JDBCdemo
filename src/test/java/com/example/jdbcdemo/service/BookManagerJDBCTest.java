package com.example.jdbcdemo.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.jdbcdemo.domain.Book;

import java.util.List;

public class BookManagerJDBCTest {

	private BookManagerJDBC bookManager = new BookManagerJDBC();
	
	private final static String TITLE_1 = "Krzyzacy";
	private final static String AUTHOR_1 = "Heniu Siena";
	private final static double PRICE_1 = 20.99;

	private final static String TITLE_2 = "QuoVadis";
	private final static String AUTHOR_2 = "Heniu Siena";
	private final static double PRICE_2 = 10.99;

	private final static String TITLE_3 = "Niebieska Mila";
	private final static String AUTHOR_3 = "Stephen Queen";
	private final static double PRICE_3 = 99.99;
	
	@Test
	public void checkConnection() {
		assertNotNull(bookManager.getConnection());
	}

	//TO PIERWSZE POWINNO
	@Before
	public void checkDeletingAllBooks() {
		bookManager.deleteAllBooks();

		List<Book> books = bookManager.getAllBooks();
		assertEquals(true, books.isEmpty());
	}

	@Test
	public void checkAdding() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);


		assertEquals(1, bookManager.addBook(book));

		List<Book> books = bookManager.getAllBooks();
		Book bookRetrived = books.get(0);

		assertEquals(TITLE_1, bookRetrived.getTitle());
		assertEquals(AUTHOR_1, bookRetrived.getAuthor());
		assertEquals(PRICE_1, bookRetrived.getPrice(), 0.01);
	}

	@Test
	public void checkFindById(){
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		bookManager.addBook(book);

		List<Book> books = bookManager.getAllBooks();
		Book bookRetrived = books.get(0);

		book = bookManager.findById(bookRetrived.getId());

		assertEquals(TITLE_1, book.getTitle());
		assertEquals(AUTHOR_1, book.getAuthor());
		assertEquals(PRICE_1, book.getPrice(), 0.01);
	}

	@Test
	public void checkFindByTitle() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);

		List<Book> books = bookManager.findByTitle(TITLE_2);
		Book bookRetrived = books.get(0);

		assertEquals(TITLE_2, bookRetrived.getTitle());
		assertEquals(AUTHOR_2, bookRetrived.getAuthor());
		assertEquals(PRICE_2, bookRetrived.getPrice(), 0.01);

	}

	@Test
	public void checkFindByAuthor() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);

		List<Book> books = bookManager.findByAuthor(AUTHOR_2);
		Book bookRetrived = books.get(0);

		assertEquals(TITLE_2, bookRetrived.getTitle());
		assertEquals(AUTHOR_2, bookRetrived.getAuthor());
		assertEquals(PRICE_2, bookRetrived.getPrice(), 0.01);
	}

	@Test
	public void checkFindByPrice() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);

		List<Book> books = bookManager.findByPrice(PRICE_2);
		Book bookRetrived = books.get(0);

		assertEquals(TITLE_2, bookRetrived.getTitle());
		assertEquals(AUTHOR_2, bookRetrived.getAuthor());
		assertEquals(PRICE_2, bookRetrived.getPrice(), 0.01);
	}
	@Test
	public void checkDeleting() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);

		assertEquals(1, bookManager.deleteBook(book2));

		List<Book> books = bookManager.getAllBooks();
		Book bookRetrived = books.get(0);

		assertEquals(1, books.size());

		assertEquals(TITLE_1, bookRetrived.getTitle());
		assertEquals(AUTHOR_1, bookRetrived.getAuthor());
		assertEquals(PRICE_1, bookRetrived.getPrice(), 0.01);
	}

	@Test
	public void checkUpdateWholeBook() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);
		assertEquals(1, bookManager.updateWholeBook(book, TITLE_3, AUTHOR_3, PRICE_3));

		Book bookRetrived = bookManager.findById(book.getId());
		assertEquals(TITLE_3, bookRetrived.getTitle());
		assertEquals(AUTHOR_3, bookRetrived.getAuthor());
		assertEquals(PRICE_3, bookRetrived.getPrice(), 0.01);
	}

	@Test
	public void checkUpdateBookTitle() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);
		assertEquals(1, bookManager.updateBookTitle(book, TITLE_3));
	}

	@Test
	public void checkUpdateBookAuthor() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);
		assertEquals(1, bookManager.updateBookAuthor(book, AUTHOR_3));
	}

	@Test
	public void checkUpdateBookPrice() {
		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
		Book book2 = new Book(TITLE_2, AUTHOR_2, PRICE_2);

		bookManager.addBook(book);
		bookManager.addBook(book2);
		assertEquals(1, bookManager.updateBookPrice(book, PRICE_3));
	}
}
