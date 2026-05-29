package com.example.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import com.example.controllers.StudentController;
import com.example.entities.Student;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class StudentDTO extends RepresentationModel<StudentDTO> {
	
	private Integer Id;
	private Integer pesel;
	private String imie;
	private String nazwisko;
	private String email;
	private Integer nrAlbumu;
	private Integer semestr;
	private String kierunek;
	
	public StudentDTO() {}
	
	public StudentDTO(Student student) {
		this.Id=student.getId();
		this.pesel = student.getPesel();
		this.imie = student.getImie();
		this.nazwisko = student.getNazwisko();
		this.email = student.getEmail();
		this.nrAlbumu=student.getNrAlbumu();
		this.semestr=student.getSemestr();
		this.kierunek=student.getKierunek();
		
		
		this.add(linkTo(methodOn(StudentController.class).getById(student.getId())).withRel("student"));
		
	}
}
