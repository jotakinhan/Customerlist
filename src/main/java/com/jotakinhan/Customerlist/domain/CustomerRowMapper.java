package com.jotakinhan.Customerlist.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

class CustomerRowMapper implements RowMapper<Customer> {

	@Override

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	Customer customer= new Customer(rowNum, null, null);
	customer.setId(rs.getInt("id"));
	customer.setFirstName(rs.getString("firstName"));
	customer.setLastName(rs.getString("lastName"));
	return customer;
	}
}
