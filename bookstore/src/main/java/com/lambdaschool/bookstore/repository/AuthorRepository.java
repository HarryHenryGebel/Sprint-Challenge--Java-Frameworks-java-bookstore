package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {}
