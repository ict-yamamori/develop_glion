package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	//従業員情報詳細画面の表示
	@GetMapping("/employeeDetail/{id}")
	public String getEmployeeDetail(Model model,@PathVariable("id") String id) {
		//従業員情報を取得
		Employee employee = empService.selectOne(id);
		model.addAttribute("employee", employee);
		
		return "employeeDetail";
	}
	
	@PostMapping("/create")
	public String postCreate(BindingResult bindingResult, Model model) {
		//入力チェックに引っかかった場合、登録画面に戻る
		
		return "index";
	}
}