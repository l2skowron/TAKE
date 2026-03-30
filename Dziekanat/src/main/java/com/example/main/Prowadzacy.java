package com.example.main;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

public class Prowadzacy extends Osoba{
	private String katedra;
	private String wydział;
	private String tytułNaukowy;
	

}
