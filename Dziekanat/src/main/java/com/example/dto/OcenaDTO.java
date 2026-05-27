package com.example.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.example.enums.TypZaliczenia;
import com.example.main.Ocena;
import com.example.main.Prowadzacy;
import com.example.main.Przedmiot;
import com.example.main.Student;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OcenaDTO extends RepresentationModel<OcenaDTO>{

	private Integer id;
	private Integer wartosc;
	private LocalDate data;
	private TypZaliczenia typZaliczenia;
	private Student student;
	private Prowadzacy prowadzacy;
	private Przedmiot przedmiot;
	
	public OcenaDTO() {};
	
	public OcenaDTO(Ocena ocena) {
		this.id = ocena.getId();
		this.wartosc = ocena.getWartosc();
		this.data=ocena.getData();
		this.typZaliczenia=ocena.getTypZaliczenia();
		this.student=ocena.getStudent();
		this.prowadzacy=ocena.getProwadzacy();
		this.przedmiot=ocena.getPrzedmiot();
	}
}
