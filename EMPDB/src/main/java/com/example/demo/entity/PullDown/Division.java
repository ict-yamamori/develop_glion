package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_division")
public class Division implements Serializable{
	
	@Id
	@Column(name = "id", nullable = false)
	private int division_id;
	
	@Column(name = "division_name", nullable = false)
	private String division_name;
}
