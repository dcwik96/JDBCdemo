package com.example.jdbcdemo.service;

import static org.junit.Assert.*;
import org.junit.Test;

import com.example.jdbcdemo.domain.Book;

public class BookManagerJDBCTest {

	BookManagerJDBC bookManager = new BookManagerJDBC();
	
	private final static String TITLE_1 = "Krzyzacy";
	private final static String AUTHOR_1 = "Heniu Siena";
	private final static double PRICE_1 = 20.99;
	
	@Test
	public void checkConnection() {
		assertNotNull(bookManager.getConnection());
	}
	
//	@Test
//	public void checkAdding() {
//		Book book = new Book(TITLE_1, AUTHOR_1, PRICE_1);
//	}
	
}
