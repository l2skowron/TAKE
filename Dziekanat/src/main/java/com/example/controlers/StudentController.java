package com.example.controlers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.example.Repositories.StudentRepository;
import com.example.dto.StudentDTO;
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
	public @ResponseBody StudentDTO getById(@PathVariable Integer id){
		Student student = studentRepo.findById(id).orElse(null);
		if(student ==null)
		{
			return null;
		}
		
		return new StudentDTO(student);
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
	
	@GetMapping("/szukaj")
	public @ResponseBody CollectionModel<StudentDTO> searchStudents(
			@RequestParam(required = false) String kierunek,
	        @RequestParam(required = false) Integer semestr,
	        @RequestParam(required = false) String nazwisko) {
		
		List<Student> repoStudent;
		
		if(kierunek != null && semestr != null) {
			repoStudent = studentRepo.findKierunekISemestr(kierunek,semestr);
		}else if(kierunek != null) {
			repoStudent = studentRepo.findKierunek(kierunek);
		}else if(semestr != null) {
			repoStudent = studentRepo.findSemestr(semestr);
		}else {
			repoStudent=(List<Student>) studentRepo.findAll();
		}
	
	
	List<StudentDTO> dtos = new ArrayList<>();
	for(Student student : repoStudent) {
		dtos.add(new StudentDTO(student));
	}
	
	CollectionModel<StudentDTO> collectionModel = CollectionModel.of(dtos);
	
	collectionModel.add(linkTo(methodOn(StudentController.class)
			.searchStudents(kierunek,semestr,nazwisko)).withSelfRel());
	
	return collectionModel;
}
	}
