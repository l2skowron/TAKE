package com.example.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.entities.Student;


public interface StudentRepository extends CrudRepository<Student, Integer> {
		List<Student> findByNazwisko(String nazwisko);

		List<Student> findByKierunekAndSemestr(String kierunek, Integer semestr);

		List<Student> findByKierunek(String kierunek);

		List<Student> findBySemestr(Integer semestr);
	@Query("SELECT s FROM Student s WHERE s.oceny IS EMPTY")
		List<Student> findStudentsWithoutOceny();
	
		long countByKierunek(String kierunek);
}
