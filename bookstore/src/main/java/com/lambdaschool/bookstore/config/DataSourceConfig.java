package com.lambdaschool.bookstore.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
  @Value("${local.run.db:h2}")
  private String dbValue;

  @Value("${spring.datasource.url}")
  private String dbURL;

  @Bean
  public DataSource dataSource() {
    if (dbValue.equalsIgnoreCase("POSTGRESQL")) {
      HikariConfig config = new HikariConfig();
      config.setDriverClassName("org.postgresql.Driver");
      config.setJdbcUrl(dbURL);
      return new HikariDataSource(config);
    } else {
      String URLString = "jdbc:h2:mem:test_db";
      String driverClass = "org.h2.Driver";
      String dbUser = "sa";
      String dbPassword = "";

      return DataSourceBuilder
        .create()
        .username(dbUser)
        .password(dbPassword)
        .url(URLString)
        .driverClassName(driverClass)
        .build();
    }
  }
}
