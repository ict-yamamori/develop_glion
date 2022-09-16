package com.example.demo.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.entity.Employee;

public interface EmployeeDao {
	public Employee selectOne(String id) throws DataAccessException;
	
	public int insertOne(Employee employee) throws DataAccessException;
}
