package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Employee;
import com.example.demo.entity.csv;
import com.example.demo.entity.csvExport;
import com.example.demo.entity.PullDown.Branch;
import com.example.demo.entity.PullDown.BusinessOrgnization;
import com.example.demo.entity.PullDown.Company;
import com.example.demo.entity.PullDown.Division;
import com.example.demo.entity.PullDown.EmpJob;
import com.example.demo.entity.PullDown.GeneralBranch;
import com.example.demo.repository.EmployeeDao;
import com.example.demo.repository.PullDown.BranchRepository;
import com.example.demo.repository.PullDown.BusOrgRepository;
import com.example.demo.repository.PullDown.CompanyRepository;
import com.example.demo.repository.PullDown.DivisionRepository;
import com.example.demo.repository.PullDown.EmpJobRepository;
import com.example.demo.repository.PullDown.GeneralBranchRepository;

@Service
public class EmpService {
	
	@Autowired
	EmployeeDao dao;
	
	@Autowired
	private BusOrgRepository busOrgRepository;
	
	@Autowired
	private DivisionRepository divisionRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private GeneralBranchRepository generalBranchRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private EmpJobRepository empJobRepository;
	
	//従業員情報 主キー検索（詳細表示）
	public Employee selectOne(int id) {
		return dao.selectOne(id);
	}
	
	public Page<Employee> selectAll(Pageable pageable) {
		return dao.selectAll(pageable);
	}
	
	public List<BusinessOrgnization> getBusinessOrgnizationAll() {
		return busOrgRepository.findAll();
	}
	
	public List<Division> getDivisionAll() {
		return divisionRepository.findAll();
	}
	
	public List<Company> getCompanyAll() {
		return companyRepository.findAll();
	}
	
	public List<GeneralBranch> getGenBraAll() {
		return generalBranchRepository.findAll();
	}
	
	public List<Branch> getBranchAll() {
		return branchRepository.findAll();
	}
	
	public List<EmpJob> getEmpJobAll() {
		return empJobRepository.findAll();
	}

	public void insertOne(Employee employee) {
		dao.insertOne(employee);
	}
	
	public void editOne(Employee employee, int id) {
		dao.editOne(employee, id);
	}
	
	public int insertSub(Employee employee, int id) {
		return dao.insertSub(employee, id);
	}
	
	public List<Employee> searchEmp(String empName, String empName_kana, String companyName) {
		return dao.searchEmp(empName, empName_kana, companyName);
	}
	
	public void importCsv(MultipartFile uploadFile) {
		dao.importCsv(uploadFile);
	}
	
	public List<csvExport> exportCsv(csv records) {
		return dao.exportCsv(records);
	}
}
