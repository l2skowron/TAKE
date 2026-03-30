package com.example.Repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.main.Student;


public interface StudentRepository extends CrudRepository<Student, Integer> {
	
		List<Student> findByNazwisko(String nazwisko);


}
