package com.example.demo.entity.PullDown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_general_branch")
public class GeneralBranch implements Serializable{
	
	@Id
	@Column(name = "id", nullable = false)
	private int gen_bra_id;
	
	@Column(name = "gen_bra_name", nullable = false)
	private String gen_bra_name;
}
