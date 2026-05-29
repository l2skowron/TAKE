package com.example.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.example.entities.Ocena;
import com.example.entities.Prowadzacy;
import com.example.entities.Przedmiot;
import com.example.entities.Student;
import com.example.enums.TypZaliczenia;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OcenaDTO extends RepresentationModel<OcenaDTO>{

	private Integer id;
	private Integer wartosc;
	private LocalDate data;
	private TypZaliczenia typZaliczenia;
	private String student;
	private String prowadzacy;
	private String nazwaPrzedmiotu;
	
	public OcenaDTO() {};
	
	public OcenaDTO(Ocena ocena) {
		this.id = ocena.getId();
		this.wartosc = ocena.getWartosc();
		this.data=ocena.getData();
		this.typZaliczenia=ocena.getTypZaliczenia();
		if (ocena.getStudent() != null) {
            this.student = ocena.getStudent().getImie() + " " + ocena.getStudent().getNazwisko();
        }
		if(ocena.getProwadzacy()!= null) {
		this.prowadzacy=ocena.getProwadzacy().getImie()+ " "+ ocena.getProwadzacy().getNazwisko();
		}
		if(ocena.getPrzedmiot()!= null) {
		this.nazwaPrzedmiotu=ocena.getPrzedmiot().getNazwa();
		}
		}
}
