package com.example.jdbcdemo.service;

import java.util.List;

import com.example.jdbcdemo.domain.Person;

public interface BooksManager {

	public int addPerson(Person person);
	public int deletePerson(Person person);
	public List<Person> getAllPersons();
	public List<Person> findByName();
	
}
