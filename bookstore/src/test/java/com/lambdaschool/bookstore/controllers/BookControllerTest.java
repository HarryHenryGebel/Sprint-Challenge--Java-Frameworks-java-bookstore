package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.services.BookService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@WithMockUser(username = "admin", roles = { "ADMIN", "DATA" })
public class BookControllerTest {
  /******
   * WebApplicationContext is needed due to security being in place.
   */
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  List<Book> bookList = new ArrayList<>();

  @Before
  public void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @After
  public void tearDown() {}

  @Test
  public void listAllBooks() throws Exception {}

  @Test
  public void getBookById() throws Exception {}

  @Test
  public void getNoBookById() throws Exception {}

  @Test
  public void addNewBook() throws Exception {}

  @Test
  public void updateFullBook() {}

  @Test
  public void deleteBookById() throws Exception {}
}
