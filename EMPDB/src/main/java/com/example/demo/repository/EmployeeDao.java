package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.entity.Employee;

public interface EmployeeDao {
	public Employee selectOne(int id) throws DataAccessException;
	
	public void insertOne(Employee employee) throws DataAccessException;

	public List<Employee> searchEmp(String empName, String empName_kana, String companyName) throws DataAccessException;
	
	public List<Employee> selectAll() throws DataAccessException;
	
	public void editOne(Employee employee, int id) throws DataAccessException;
	
	public int insertSub(Employee employee, int id) throws DataAccessException;
}
