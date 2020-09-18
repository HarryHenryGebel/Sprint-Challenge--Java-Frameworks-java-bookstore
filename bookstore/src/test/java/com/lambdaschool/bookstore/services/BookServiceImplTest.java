package com.lambdaschool.bookstore.services;

import static junit.framework.TestCase.assertEquals;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.Wrote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
public class BookServiceImplTest {
  @Autowired
  private BookService bookService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() {}

  @Test
  public void findAll() {}

  @Test
  public void findBookById() {}

  @Test(expected = ResourceNotFoundException.class)
  public void notFindBookById() {}

  @Test
  public void delete() {}

  @Test
  public void save() {}

  @Test
  public void update() {}

  @Test
  public void deleteAll() {}
}
