package com.example.Repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.main.Student;


public interface StudentRepository extends CrudRepository<Student, Integer> {
		List<Student> findByNazwisko(String nazwisko);

		List<Student> findKierunekISemestr(String kierunek, Integer semestr);

		List<Student> findKierunek(String kierunek);

		List<Student> findSemestr(Integer semestr);

	
}
