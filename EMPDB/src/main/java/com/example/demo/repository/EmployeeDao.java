package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Employee;
import com.example.demo.entity.csv;
import com.example.demo.entity.csvExport;

public interface EmployeeDao {
	public Employee selectOne(int id) throws DataAccessException;
	
	public void insertOne(Employee employee) throws DataAccessException;

	public List<Employee> searchEmp(String empName, String empName_kana, String companyName) throws DataAccessException;
	
	public Page<Employee> selectAll(Pageable pageable) throws DataAccessException;
	
	public void editOne(Employee employee, int id) throws DataAccessException;
	
	public int insertSub(Employee employee, int id) throws DataAccessException;
	
	public void importCsv(MultipartFile uploadFile);
	
	public List<csvExport> exportCsv(csv records);
}
