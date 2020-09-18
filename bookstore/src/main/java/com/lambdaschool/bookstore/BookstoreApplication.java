package com.lambdaschool.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main class to start the application.
 */
@EnableJpaAuditing
@SpringBootApplication
public class BookstoreApplication {
  /**
   * If an environment variable is not found, set this to true
   */
  private static boolean stop = false;

  /**
   * If an application relies on an environment variable, check to make sure that environment variable is available!
   * If the environment variable is not available, you could set a default value, or as is done here, stop execution of the program
   *
   * @param variable The system environment where environment variable live
   */
  private static void checkEnvironmentVariable(String variable) {
    if (System.getenv(variable) == null) {
      stop = true;
    }
  }

  /**
   * Main method to start the application.
   *
   * @param args Not used in this application.
   */
  public static void main(String[] args) {
    // Check to see if the environment variables exists. If they do not, stop execution of application.
    checkEnvironmentVariable("CLIENT_ID");
    checkEnvironmentVariable("CLIENT_SECRET");

    if (!stop) {
      // so run the application!
      SpringApplication.run(BookstoreApplication.class, args);
    }
  }
}
