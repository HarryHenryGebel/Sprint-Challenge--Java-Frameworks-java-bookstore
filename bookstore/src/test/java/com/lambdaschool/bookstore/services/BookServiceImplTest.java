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
  public void findAll() {
    assertEquals(4, bookService.findAll().size());
  }

  @Test
  public void findBookById() {
    assertEquals(26, bookService.findBookById(26).getBookid());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void notFindBookById() {
    assertEquals(5, bookService.findBookById(9001).getBookid());
  }

  @Test
  public void delete() {
    bookService.delete(26);
    assertEquals(4, bookService.findAll().size());
  }

  @Test
  public void save() {
    Section s1 = new Section("Fiction");
    s1.setSectionid(1);
    Author a100 = new Author("John", "Mitchell");
    a100.setAuthorid(1);
    Book testB = new Book("TestTitle", "1239287288289", 2076, s1);
    testB.getWrotes().add(new Wrote(a100, new Book()));

    assertEquals(testB.getTitle(), "TestTitle");
    assertEquals(testB.getIsbn(), "1239287288289");
    assertEquals(testB.getCopy(), 2076);
    assertEquals(testB.getSection().getName(), s1.getName());
  }

  @Test
  public void update() {}

  @Test
  public void deleteAll() {}
}
