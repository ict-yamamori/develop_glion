package com.example.demo.repository.PullDown;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PullDown.BusinessOrgnization;

@Repository
public interface BusOrgRepository extends JpaRepository<BusinessOrgnization, String>{
	
}
