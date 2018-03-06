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

import com.jotakinhan.Customerlist.CustomerlistApplication;

@Controller
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerlistApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @RequestMapping(value="/customerlist")
    public String studentList(Model model) {	
        // Fetch all customers
        List<Map<String, Object>> customers = jdbcTemplate.queryForList("select first_name, last_name from customer");
        
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        
        // Add customerlist to model and return to view
        model.addAttribute("customers", customers);
        return "customerlist";
    }	
	
}
