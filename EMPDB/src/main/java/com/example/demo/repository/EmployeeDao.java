package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.entity.Employee;

public interface EmployeeDao {
	public Employee selectOne(String id) throws DataAccessException;
	
	public int insertOne(Employee employee) throws DataAccessException;

	public 	List<Employee> searchEmp(String name, String name_kana, String company_name);
}
