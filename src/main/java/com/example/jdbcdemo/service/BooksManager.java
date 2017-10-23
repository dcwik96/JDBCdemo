package com.example.jdbcdemo.service;

import java.util.List;

import com.example.jdbcdemo.domain.Book;

public interface BooksManager {

	public int adBook(Book book);
	public int deleteBook(Book book);
	public List<Book> getAllBooks();
	public List<Book> findByTitle();
	
}
