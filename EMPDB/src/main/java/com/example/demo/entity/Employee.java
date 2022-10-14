package com.example.demo.entity;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Employee {
	private int id;
	@NotBlank(message = "※省略することはできません")
	private String name;
	@NotBlank(message = "※省略することはできません")
	private String name_kana;
	//@NotBlank(message = "省略することはできません")
	private String status;
	private String telephonenumber;
	private String mailaddress;
	@NotNull(message = "※省略することはできません")
	private Date entering_date;
	private Date leaving_date;
	private String business_org_name;
	private String division_name;
	//@NotBlank(message = "省略することはできません")
	private String company_name;
	private String gen_bra_name;
	private String branch_name;
	private String department;
	//@NotBlank(message = "省略することはできません")
	private String emp_job_name;
	private String official_position;
	//@NotBlank(message = "省略することはできません")
	private String employment_type;
	@NotNull(message = "※省略することはできません")
	private Date start_date;
	private Date end_date;
	private String second_company_name;
	
	private Integer employee_id;
	private Integer business_org_id;
	private Integer division_id;
	@NotNull(message = "※省略することはできません")
	private Integer company_id;
	private Integer gen_bra_id;
	private Integer branch_id;
	private Integer emp_job_id;
	private boolean org_kbn;
	private Integer second_company_id;
}
