package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeDao;

@Service
public class EmpService {
	
	@Autowired
	EmployeeDao dao;
	
	//従業員情報 主キー検索（詳細表示）
	public Employee selectOne(String id) {
		return dao.selectOne(id);
	}
	
	public boolean insert(Employee employee) {
		//insert実行
		int rowNumber = dao.insertOne(employee);
		
		//判定用変数
		boolean result = false;
		
		if (rowNumber > 0) {
			//insert成功
			result = true;
		}
		
		return result;
	}
}
