package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class csv {
	List <String> name;
	List <String> name_kana;
	List <String> status;
	List <Date> entering_date;
	List <Date> leaving_date;
	List <String> employment_type;
	List <String> mail_address;
	List <String> telephone_number;
	List <String> business_org;
	List <String> division;
	List <String> company;
	List <String> general_branch;
	List <String> branch;
	List <String> department;
	List <String> official_position;
	List <String> emp_job;
	List <String> bus_org;
	List <Date> start_date;
	List <Date> end_date;
}
