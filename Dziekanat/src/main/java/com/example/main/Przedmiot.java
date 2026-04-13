package com.example.main;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Przedmiot {
	@Id
	@GeneratedValue
	private Integer id;
	private String nazwa;
	private Integer ECTS;
	private Integer NumerSemestru;
	private String kierunek;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "przedmiot")
	Set<Ocena> ocena;
}
 

