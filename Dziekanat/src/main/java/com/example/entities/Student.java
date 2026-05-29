package com.example.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Student extends Osoba {
	@Column(unique = true)
	private Integer nrAlbumu;
	private Integer semestr;
	private String kierunek;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "student")
 	private Set<Ocena> oceny;
}
