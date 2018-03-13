package com.jotakinhan.Customerlist;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jotakinhan.Customerlist.domain.Customer;

@SpringBootApplication
public class CustomerlistApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(CustomerlistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomerlistApplication.class, args);
	}
	   
	@Bean
	public CommandLineRunner demo(JdbcTemplate jdbcTemplate) {
		return (args) -> {
			// Database is created by using resources/schema.sql
			
			// Insert some demo data
	        jdbcTemplate.update("insert into customer(firstName, lastName) values (?, ?)", "John", "West");
	        jdbcTemplate.update("insert into customer(firstName, lastName) values (?, ?)", "Mike", "Mars");
	        jdbcTemplate.update("insert into customer(firstName, lastName) values (?, ?)", "Kate", "Johnson");
		};
	}	
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
    public void run (String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE IF EXISTS customer");
        jdbcTemplate.execute("CREATE TABLE customer(" +
                "id SERIAL, firstName VARCHAR(255), lastName VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customer(firstName, lastName) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where firstName = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, firstName, lastName FROM customer WHERE firstName = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"))
        ).forEach(customer -> log.info(customer.toString()));
    }

}
