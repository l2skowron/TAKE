package com.example.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Student extends Osoba {
	@Column(unique = true)
	private Integer nrAlbumu;
	private Integer semestr;
	private String kierunek;
}
