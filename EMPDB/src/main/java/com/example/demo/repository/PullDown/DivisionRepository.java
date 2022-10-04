package com.example.demo.repository.PullDown;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PullDown.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String>{
	
}
