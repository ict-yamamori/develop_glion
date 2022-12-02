package com.example.demo.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository("EmployeeDaoJdbcImpl")
public class EmployeeDaoJdbcImpl implements EmployeeDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//従業員データを取得（詳細表示）
	@Override
	public Employee selectOne(String id) throws DataAccessException {
		
		//一件取得
		Map<String, Object> map = jdbcTemplate.queryForMap("select t_orgnization.id,m_employee.name,m_employee.name_kana,m_employee.status,m_employee.entering_date,m_employee.leaving_date,employment_type, org_kbn,m_employee.mail_address,m_employee.telephone_number,m_business_org.business_org_name,m_division.division_name,m_company.company_name,m_general_branch.gen_bra_name,m_branch.branch_name,department,official_position,m_emp_job.emp_job_name,start_date,end_date,m_company.company_name FROM t_orgnization LEFT OUTER JOIN m_division ON m_division.id = t_orgnization.division_id JOIN m_company ON m_company.id = t_orgnization.company_id JOIN m_business_org ON m_business_org.id = t_orgnization.business_org_id LEFT OUTER JOIN m_general_branch ON m_general_branch.id = t_orgnization.gen_bra_id LEFT OUTER JOIN m_branch ON m_branch.id = t_orgnization.branch_id LEFT OUTER JOIN m_emp_job ON m_emp_job.id = t_orgnization.emp_job_id LEFT OUTER JOIN m_employee ON m_employee.id = t_orgnization.employee_id" + " where t_orgnization.id = ?", id);
		
		//出向情報のみ別SQLで取得（一括でやる方法わからない）
		Map<String, Object> second_comp = jdbcTemplate.queryForMap("select m_company.company_name from t_orgnization LEFT OUTER JOIN m_company ON m_company.id = t_orgnization.second_company_id" + " where t_orgnization.id = ?", id);
		
		//結果返却用の変数
		Employee employee = new Employee();
		
		//取得したデータを結果返却用の変数にセットしていく
		employee.setId((int) map.get("id"));
		employee.setName((String) map.get("name"));
		employee.setMailaddress((String) map.get("mail_address"));
		employee.setName_kana((String) map.get("name_kana"));
		employee.setStatus((String) map.get("status"));
		employee.setEntering_date((Date) map.get("entering_date"));
		employee.setLeaving_date((Date) map.get("leaving_date"));
		employee.setEmployment_type((String) map.get("employment_type"));
		employee.setTelephonenumber((String) map.get("telephone_number"));
		employee.setBusiness_org_name((String) map.get("business_org_name"));
		employee.setDivision_name((String) map.get("division_name"));
		employee.setCompany_name((String) map.get("company_name"));
		employee.setGen_bra_name((String) map.get("gen_bra_name"));
		employee.setBranch_name((String) map.get("branch_name"));
		employee.setDepartment((String) map.get("department"));
		employee.setOfficial_position((String) map.get("official_position"));
		employee.setEmp_job_name((String) map.get("emp_job_name"));
		employee.setStart_date((Date) map.get("start_date"));
		employee.setEnd_date((Date) map.get("end_date"));
		employee.setOrg_kbn((boolean) map.get("org_kbn"));
		employee.setSecond_company_name((String) second_comp.get("company_name"));
		
		return employee;
	}

	@Override
	public int insertOne(Employee employee) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public List<Employee> searchEmp(String name, String name_kana, String company_name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
}