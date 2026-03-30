package com.example.controlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Repositories.StudentRepository;
import com.example.main.Student;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentRepository studentRepo;
	@PostMapping
	public @ResponseBody String addStudent(@RequestBody Student student) {
		student= studentRepo.save(student);
		return "Added with id=" + student.getId();
	}
		
	@GetMapping("/{id}")
	public @ResponseBody Optional<Student> getById(@PathVariable Integer id){
		return studentRepo.findById(id);
	}
	@GetMapping
	public @ResponseBody Iterable<Student> getAll(){
		return studentRepo.findAll();
	}
	@PutMapping
	public @ResponseBody String editStudent(@RequestBody Student student) {
		if(student.getId()==null) {
			return "Podaj id aby edytowac. ";
		}
		studentRepo.save(student);
		return "Zaktualizowano dane studenta o id= " + student.getId();
		}
	
	@DeleteMapping("/{id}")
	public @ResponseBody String delete(@PathVariable Integer id) {
		studentRepo.deleteById(id);
		return "Usunięto studenta o id=" +id;	
	}
	
}
