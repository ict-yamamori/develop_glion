package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class HomeController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//従業員情報一覧表示
	@GetMapping("/")
	private String index(Model model) {
		String sql = "select t_orgnization.id,m_employee.name,m_company.company_name,m_general_branch.gen_bra_name,m_branch.branch_name,m_emp_job.emp_job_name,employment_type,m_employee.entering_date,m_employee.status,official_position FROM t_orgnization LEFT OUTER JOIN m_division ON m_division.id = t_orgnization.division_id JOIN m_company ON m_company.id = t_orgnization.company_id JOIN m_business_org ON m_business_org.id = t_orgnization.business_org_id LEFT OUTER JOIN m_general_branch ON m_general_branch.id = t_orgnization.gen_bra_id LEFT OUTER JOIN m_branch ON m_branch.id = t_orgnization.branch_id LEFT OUTER JOIN m_emp_job ON m_emp_job.id = t_orgnization.emp_job_id LEFT OUTER JOIN m_employee ON m_employee.id = t_orgnization.employee_id";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		model.addAttribute("employeeList", list);
		return "index";
	}
}