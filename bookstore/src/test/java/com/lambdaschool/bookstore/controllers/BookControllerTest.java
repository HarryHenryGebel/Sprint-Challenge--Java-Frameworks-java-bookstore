package com.lambdaschool.bookstore.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.models.*;
import com.lambdaschool.bookstore.services.BookService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
    bookList = new ArrayList<>();

    Role role1 = new Role("admin");
    Role role2 = new Role("user");
    Role role3 = new Role("data");

    role1.setRoleid(1);
    role2.setRoleid(2);
    role3.setRoleid(3);

    // admin, data, user
    User user1 = new User("admin", "password", "admin@lambdaschool.local");
    user1.getRoles().add(new UserRoles(user1, role1));
    user1.getRoles().add(new UserRoles(user1, role2));
    user1.getRoles().add(new UserRoles(user1, role3));
    user1.getUseremails().add(new Useremail(user1, "admin@email.local"));
    user1.getUseremails().add(new Useremail(user1, "admin@mymail.local"));

    user1.setUserid(1);

    // data, user
    User user2 = new User("cinnamon", "1234567", "cinnamon@lambdaschool.local");
    user2.getRoles().add(new UserRoles(user2, role2));
    user2.getRoles().add(new UserRoles(user2, role3));
    user2.getUseremails().add(new Useremail(user2, "cinnamon@mymail.local"));
    user2.getUseremails().add(new Useremail(user2, "hops@mymail.local"));
    user2.getUseremails().add(new Useremail(user2, "bunny@email.local"));
    user2.setUserid(2);

    // user
    User user3 = new User(
      "barnbarn",
      "ILuvM4th!",
      "barnbarn@lambdaschool.local"
    );
    user3.getRoles().add(new UserRoles(user3, role2));
    user3.getUseremails().add(new Useremail(user3, "barnbarn@email.local"));
    user3.setUserid(3);

    User user4 = new User("puttat", "password", "puttat@school.lambda");
    user4.getRoles().add(new UserRoles(user4, role2));
    user4.setUserid(4);

    User user5 = new User("misskitty", "password", "misskitty@school.lambda");
    user5.getRoles().add(new UserRoles(user5, role2));
    user5.setUserid(5);

    // Seed Books

    Author author1 = new Author("John", "Mitchell");
    Author author2 = new Author("Dan", "Brown");
    Author author3 = new Author("Jerry", "Poe");
    Author author4 = new Author("Wells", "Teague");
    Author author5 = new Author("George", "Gallinger");
    Author author6 = new Author("Ian", "Stewart");

    author1.setAuthorid(1);
    author2.setAuthorid(2);
    author3.setAuthorid(3);
    author4.setAuthorid(4);
    author5.setAuthorid(5);
    author6.setAuthorid(6);

    Section section1 = new Section("Fiction");
    Section section2 = new Section("Technology");
    Section section3 = new Section("Travel");
    Section section4 = new Section("Business");
    Section section5 = new Section("Religion");

    section1.setSectionid(1);
    section2.setSectionid(2);
    section3.setSectionid(3);
    section4.setSectionid(4);
    section5.setSectionid(5);

    Book book1 = new Book("Flatterland", "9780738206752", 2001, section1);
    book1.getWrotes().add(new Wrote(author6, new Book()));
    book1.setBookid(1);

    Book book2 = new Book("Digital Fortess", "9788489367012", 2007, section1);
    book2.getWrotes().add(new Wrote(author2, new Book()));
    book2.setBookid(2);

    Book book3 = new Book("The Da Vinci Code", "9780307474278", 2009, section1);
    book3.getWrotes().add(new Wrote(author2, new Book()));
    book3.setBookid(3);

    Book book4 = new Book(
      "Essentials of Finance",
      "1314241651234",
      0,
      section4
    );
    book4.getWrotes().add(new Wrote(author3, new Book()));
    book4.getWrotes().add(new Wrote(author5, new Book()));
    book4.setBookid(4);

    Book book5 = new Book(
      "Calling Texas Home",
      "1885171382134",
      2000,
      section3
    );
    book5.getWrotes().add(new Wrote(author4, new Book()));
    book5.setBookid(5);

    bookList.add(book1);
    bookList.add(book2);
    bookList.add(book3);
    bookList.add(book4);
    bookList.add(book5);

    //The following is needed due to security being in place!
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @After
  public void tearDown() {}

  @Test
  public void listAllBooks() throws Exception {
    String apiUrl = "/books/books";

    Mockito.when(bookService.findAll()).thenReturn(bookList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .get(apiUrl)
      .accept(MediaType.APPLICATION_JSON);

    MvcResult r = mockMvc.perform(requestBuilder).andReturn();
    String tr = r.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    String er = mapper.writeValueAsString(bookList);

    assertEquals("Rest API Returns List", er, tr);
  }

  @Test
  public void getBookById() throws Exception {
    String apiUrl = "/books/book/26";

    Mockito.when(bookService.findBookById(26)).thenReturn(bookList.get(0));
    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .get(apiUrl)
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String testResult = result.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    String errorResult = mapper.writeValueAsString(bookList.get(0));

    assertEquals("Rest API Returns List", errorResult, testResult);
  }

  @Test
  public void getNoBookById() throws Exception {
    String apiUrl = "/books/book/9001";

    Mockito.when(bookService.findBookById(9001)).thenReturn(null);

    RequestBuilder rb = MockMvcRequestBuilders
      .get(apiUrl)
      .accept(MediaType.APPLICATION_JSON);

    MvcResult r = mockMvc.perform(rb).andReturn();
    String tr = r.getResponse().getContentAsString();

    String er = "";

    assertEquals("Rest API Returns List", er, tr);
  }

  @Test
  public void addNewBook() throws Exception {
    String apiUrl = "/books/book";

    Section s1 = new Section("Fiction");
    Author a1 = new Author("John", "Mitchell");

    Book b6 = new Book("TestTitle", "1239287288289", 2021, s1);
    b6.getWrotes().add(new Wrote(a1, new Book()));
    b6.setBookid(1);

    ObjectMapper mapper = new ObjectMapper();
    String bookString = mapper.writeValueAsString(b6);

    Mockito.when(bookService.save(any(Book.class))).thenReturn(b6);

    RequestBuilder rb = MockMvcRequestBuilders
      .post(apiUrl)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .content(bookString);

    mockMvc
      .perform(rb)
      .andExpect(status().isCreated())
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void deleteBookById() throws Exception {
    String apiUrl = "/books/book/{bookid}";

    RequestBuilder rb = MockMvcRequestBuilders
      .delete(apiUrl, "6")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON);
    mockMvc
      .perform(rb)
      .andExpect(status().is2xxSuccessful())
      .andDo(MockMvcResultHandlers.print());
  }
}
