package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RankingDTO extends RepresentationModel<RankingDTO> {
	
	private Integer id;
	private String imie;
	private String nazwisko;
	Double srednia;
	
	public RankingDTO(){}
	
	public RankingDTO(Integer id, String imie, String nazwisko, Double srednia) {
	this.id = id;
	this.imie = imie;
	this.nazwisko=nazwisko;
	this.srednia = srednia;
	}

}
