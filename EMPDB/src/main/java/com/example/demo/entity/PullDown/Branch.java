package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_branch")
public class Branch implements Serializable{
	
	@Id
	@Column(name = "id", nullable = true)
	private int branch_id;
	
	@Column(name = "branch_name", nullable = true)
	private String branch_name;
}
