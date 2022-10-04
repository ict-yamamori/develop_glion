package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_emp_job")
public class EmpJob implements Serializable{
	
	@Id
	@Column(name = "id", nullable = false)
	private int emp_job_id;
	
	@Column(name = "emp_job_name", nullable = false)
	private String emp_job_name;
}
