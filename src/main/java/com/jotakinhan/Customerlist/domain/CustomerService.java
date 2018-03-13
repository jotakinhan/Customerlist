package com.jotakinhan.Customerlist.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
    JdbcTemplate template;

    public List<Customer> findAll() {
        String sql = "select * from customer";
        RowMapper<Customer> rm = new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            	Customer user = new Customer(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"));
                /*String email = resultSet.getString("email");
                if (email != null) {
                    user.setEmail(email);
                }*/

                return user;
            }
        };

        return template.query(sql, rm);
	
}
}