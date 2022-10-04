package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_company")
public class Company implements Serializable{
	
	@Id
	@Column(name = "id", nullable = false)
	private int company_id;
	
	@Column(name = "company_name", nullable = false)
	private String company_name;
}