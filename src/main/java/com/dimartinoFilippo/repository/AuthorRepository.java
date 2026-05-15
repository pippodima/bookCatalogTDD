package com.dimartinoFilippo.repository;

import java.util.List;

import com.dimartinoFilippo.model.Author;

public interface AuthorRepository {

	Author findById(String id);

	List<Author> findAll();

}
