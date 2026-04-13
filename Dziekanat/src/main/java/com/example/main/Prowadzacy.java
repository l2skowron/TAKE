package com.example.main;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

public class Prowadzacy extends Osoba{
	private String katedra;
	private String wydział;
	private String tytułNaukowy;
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy= "prowadzacy")
	Set<Ocena> ocena;
}
