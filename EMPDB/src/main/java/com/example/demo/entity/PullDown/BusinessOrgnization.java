package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_business_org")
public class BusinessOrgnization implements Serializable{
	
	@Id
	@Column(name = "id", nullable = false)
	private int business_org_id;
	
	@Column(name = "business_org_name", nullable = false)
	private String business_org_name;
}
