package com.example.demo.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Employee {
	private int id;
	private String name;
	private String name_kana;
	private String status;
	private String telephonenumber;
	private String mailaddress;
	private Date entering_date;
	private Date leaving_date;
	private String business_org_name;
	private String division_name;
	private String company_name;
	private String gen_bra_name;
	private String branch_name;
	private String department;
	private String emp_job_name;
	private String official_position;
	private String employment_type;
	private Date start_date;
	private Date end_date;
}
