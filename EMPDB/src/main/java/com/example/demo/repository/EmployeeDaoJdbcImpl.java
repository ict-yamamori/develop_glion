package com.example.demo.repository;

import java.sql.Date;
import java.util.ArrayList;
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
	public Employee selectOne(int id) throws DataAccessException {
		
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
	public void insertOne(Employee employee) throws DataAccessException {
		jdbcTemplate.update("insert into m_employee (name,name_kana,status,telephone_number,mail_address,entering_date)"
		+ " values(?, ?, " + "\"在職\"" + ", ?, ?, ?)",
		employee.getName(),
		employee.getName_kana(),
		employee.getTelephonenumber(),
		employee.getMailaddress(),
		employee.getEntering_date());

		//M_EMPLOYEEの最終更新ID取得
		int emp_id = jdbcTemplate.queryForObject("select MAX(id) from m_employee",Integer.class);
		
		//T_ORGNIZATIONの登録処理
		jdbcTemplate.update("insert into t_orgnization (employee_id,business_org_id,division_id,company_id,gen_bra_id,branch_id,department,emp_job_id,official_position,employment_type,org_kbn,start_date,second_company_id)"
			+ "values(" + emp_id +",?,?,?,?,?,?,?,?,?,?,?,?)",
			employee.getBusiness_org_id(),
			employee.getDivision_id(),
			employee.getCompany_id(),
			employee.getGen_bra_id(),
			employee.getBranch_id(),
			employee.getDepartment(),
			employee.getEmp_job_id(),
			employee.getOfficial_position(),
			employee.getEmployment_type(),
			employee.isOrg_kbn(),
			employee.getStart_date(),
			employee.getSecond_company_id());
	}

	@Override
	public List<Employee> searchEmp(String empName, String empName_kana, String companyName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t_orgnization.id,m_employee.name,m_employee.name_kana,m_employee.status,m_employee.entering_date,m_employee.leaving_date,employment_type,org_kbn,nullif(m_employee.mail_address,''),nullif(m_employee.telephone_number,''),m_business_org.business_org_name,m_division.division_name,m_company.company_name,m_second_company.company_name as B,m_general_branch.gen_bra_name,m_branch.branch_name,department,official_position,m_emp_job.emp_job_name,org_kbn,start_date,end_date FROM t_orgnization LEFT OUTER JOIN m_division ON m_division.id = t_orgnization.division_id JOIN m_company ON m_company.id = t_orgnization.company_id LEFT OUTER JOIN m_second_company ON m_second_company.id = t_orgnization.second_company_id JOIN m_business_org ON m_business_org.id = t_orgnization.business_org_id LEFT OUTER JOIN m_general_branch ON m_general_branch.id = t_orgnization.gen_bra_id LEFT OUTER JOIN m_branch ON m_branch.id = t_orgnization.branch_id LEFT OUTER JOIN m_emp_job ON m_emp_job.id = t_orgnization.emp_job_id LEFT OUTER JOIN m_employee ON m_employee.id = t_orgnization.employee_id");
		
		if (!"".equals(empName)) {
			sql.append(" where employee_id in (select id from m_employee where name like '%" + empName + "%')");
			if (!"".equals(empName_kana)) {
				sql.append(" and employee_id in (select id from m_employee where name_kana like '%" + empName_kana + "%')");
			}
			if (!"".equals(companyName)) {
				sql.append(" and company_id in (select id from m_company where company_name like '%" + companyName + "%') or employee_id in (select id from m_employee where name like '%" + empName + "%') and second_company_id in (select id from m_second_company where company_name like '%" + companyName + "%')");
			}
		} else if (!"".equals(empName_kana)) {
			sql.append(" where employee_id in (select id from m_employee where name_kana like '%" + empName_kana + "%')");
			if (!"".equals(empName)) {
				sql.append(" and employee_id in (select id from m_employee where name like '%" + empName + "%')");
			}
			if (!"".equals(companyName)) {
				sql.append(" and company_id in (select id from m_company where company_name like '%" + companyName + "%') or employee_id in (select id from m_employee where name_kana like '%" + empName_kana + "%') and second_company_id in (select id from m_second_company where company_name like '%" + companyName + "%')");
			}
		} else if (!"".equals(companyName)) {
			sql.append(" where company_id in (select id from m_company where company_name like '%" + companyName + "%') or second_company_id in (select id from m_second_company where company_name like '%" + companyName + "%')");
			
			if (!"".equals(empName_kana)) {
				sql.append(" and employee_id in (select id from m_employee where name_kana like '%" + empName_kana + "%')");
			}
			if (!"".equals(empName)) {
				sql.append(" and employee_id in (select id from m_employee where name like '%" + empName + "%')");
			}
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		
		//結果返却用の変数
		List<Employee> employeeList = new ArrayList<>();
		
		// 取得したデータを結果返却用のemployeeListに格納していく
		for (Map<String, Object> map : list) {
			Employee employee = new Employee();
			
			//Employeeインスタンスに取得したデータをセットする
			employee.setId((int) map.get("id"));
			employee.setName((String) map.get("name"));
			employee.setName_kana((String) map.get("name_kana"));
			employee.setStatus((String) map.get("status"));
			employee.setEntering_date((Date) map.get("entering_date"));
			employee.setLeaving_date((Date) map.get("leaving_date"));
			employee.setEmployment_type((String) map.get("employment_type"));
			employee.setMailaddress((String) map.get("mailaddress"));
			employee.setTelephonenumber((String) map.get("telephonenumber"));
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
			employee.setSecond_company_name((String) map.get("second_company_name"));
			
			//結果返却用のemployeeListに追加
			employeeList.add(employee);
		}
		
		return employeeList;
	}

	@Override
	public List<Employee> selectAll() throws DataAccessException {
		
		String sql = "select t_orgnization.id,m_employee.name,m_company.company_name,m_general_branch.gen_bra_name,m_branch.branch_name,m_emp_job.emp_job_name,employment_type,m_employee.entering_date,m_employee.status,official_position FROM t_orgnization LEFT OUTER JOIN m_division ON m_division.id = t_orgnization.division_id JOIN m_company ON m_company.id = t_orgnization.company_id JOIN m_business_org ON m_business_org.id = t_orgnization.business_org_id LEFT OUTER JOIN m_general_branch ON m_general_branch.id = t_orgnization.gen_bra_id LEFT OUTER JOIN m_branch ON m_branch.id = t_orgnization.branch_id LEFT OUTER JOIN m_emp_job ON m_emp_job.id = t_orgnization.emp_job_id LEFT OUTER JOIN m_employee ON m_employee.id = t_orgnization.employee_id";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		//結果返却用の変数
		List<Employee> employeeList = new ArrayList<>();
		
		// 取得したデータを結果返却用のemployeeListに格納していく
		for (Map<String, Object> map : list) {
			Employee employee = new Employee();
			
			//Employeeインスタンスに取得したデータをセットする
			employee.setId((int) map.get("id"));
			employee.setName((String) map.get("name"));
			employee.setStatus((String) map.get("status"));
			employee.setEntering_date((Date) map.get("entering_date"));
			employee.setEmployment_type((String) map.get("employment_type"));
			employee.setCompany_name((String) map.get("company_name"));
			employee.setGen_bra_name((String) map.get("gen_bra_name"));
			employee.setBranch_name((String) map.get("branch_name"));
			employee.setEmp_job_name((String) map.get("emp_job_name"));
			
			//結果返却用のemployeeListに追加
			employeeList.add(employee);
		}
		
		return employeeList;
	}

	@Override
	public void editOne(Employee employee, int id) throws DataAccessException {
		//empidを取得する
		int emp_id = jdbcTemplate.queryForObject("select employee_id from t_orgnization where id = " + id,Integer.class);
		
		//M_EMPLOYEEの更新処理
		jdbcTemplate.update("update m_employee set name = ?,name_kana = ?,status = ?,telephone_number = ?,mail_address = ?,entering_date = ?, leaving_date = ? where id = " + emp_id,
				employee.getName(),
				employee.getName_kana(),
				employee.getStatus(),
				employee.getTelephonenumber(),
				employee.getMailaddress(),
				employee.getEntering_date(),
				employee.getLeaving_date());
		
		//T_ORGNIZATIONの更新処理
		jdbcTemplate.update("update t_orgnization set employee_id = " + emp_id + ",business_org_id = ?,division_id = ?,company_id = ?,gen_bra_id = ?,branch_id = ?,department = ?,emp_job_id = ?,official_position = ?,employment_type = ?,org_kbn = ?,start_date = ?,end_date = ?,second_company_id = ? where id = " + id,
				employee.getBusiness_org_id(),
				employee.getDivision_id(),
				employee.getCompany_id(),
				employee.getGen_bra_id(),
				employee.getBranch_id(),
				employee.getDepartment(),
				employee.getEmp_job_id(),
				employee.getOfficial_position(),
				employee.getEmployment_type(),
				employee.isOrg_kbn(),
				employee.getStart_date(),
				employee.getEnd_date(),
				employee.getSecond_company_id());
		
	}

	@Override
	public int insertSub(Employee employee, int id) throws DataAccessException {
		//empidを取得する
		int emp_id = jdbcTemplate.queryForObject("select employee_id from t_orgnization where id = " + id,Integer.class);
		
		//T_ORGNIZATIONの兼務情報をインサートする
		jdbcTemplate.update("insert into t_orgnization (employee_id,business_org_id,division_id,company_id,gen_bra_id,branch_id,department,emp_job_id,official_position,employment_type,org_kbn,start_date)"
				+ "values(" + emp_id +",?,?,?,?,?,?,?,?,?," + false + ",?)",
				employee.getBusiness_org_id(),
				employee.getDivision_id(),
				employee.getCompany_id(),
				employee.getGen_bra_id(),
				employee.getBranch_id(),
				employee.getDepartment(),
				employee.getEmp_job_id(),
				employee.getOfficial_position(),
				employee.getEmployment_type(),
				employee.getStart_date());
		
		//兼務情報として登録したIDを取得する
		int sub_id = jdbcTemplate.queryForObject("select MAX(id) from t_orgnization",Integer.class);
		
		return sub_id;
	}
	
}