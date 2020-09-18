package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {}
