package com.dimartinoFilippo.repository;

import com.dimartinoFilippo.model.Author;

public interface AuthorRepository {

	Author findById(String id);

}
