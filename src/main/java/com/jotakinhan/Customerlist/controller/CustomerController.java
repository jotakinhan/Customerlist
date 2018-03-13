package com.jotakinhan.Customerlist.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jotakinhan.Customerlist.CustomerlistApplication;
import com.jotakinhan.Customerlist.domain.Customer;
import com.jotakinhan.Customerlist.domain.CustomerRepository;
import com.jotakinhan.Customerlist.domain.CustomerService;

@Controller
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerlistApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    CustomerService service;
    
    @Autowired
	private CustomerRepository repository;
    
    @RequestMapping(value="/customerlist")
    public String studentList(Model model) {	
        // Fetch all customers
        List<Map<String, Object>> customers = jdbcTemplate.queryForList("select firstName, lastName from customer");
        
        log.info("Creating tables");
        
        log.info("Querying for customer records:");
        jdbcTemplate.query(
                "SELECT id, firstName, lastName FROM customer",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"))
        ).forEach(customer -> log.info(customer.toString()));
        
        //jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        
        // Add customerlist to model and return to view
        //List<Customer> customers1 = repository.findAll();
        model.addAttribute("customers", repository.findAll());
        return "customerlist";
    }
    
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String index(Model md){
    	
    	log.info("Querying for customer records:");
        jdbcTemplate.query(
                "SELECT id, firstName, lastName FROM customer",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"))
        ).forEach(customer -> log.info(customer.toString()));
    	
        md.addAttribute("customers", service.findAll());

        return "customers";
    }
	
}
