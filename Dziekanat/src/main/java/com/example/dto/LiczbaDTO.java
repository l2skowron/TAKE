package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class LiczbaDTO extends RepresentationModel<LiczbaDTO> {
	private long wartosc;
	private String opis;
	
	
	public LiczbaDTO() {}
	
	public LiczbaDTO(long wartosc, String opis) {
		this.wartosc = wartosc;
		this.opis = opis;
	}

}
