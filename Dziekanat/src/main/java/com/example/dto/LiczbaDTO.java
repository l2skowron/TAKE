package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

public class LiczbaDTO extends RepresentationModel<LiczbaDTO> {
	private long wartosc;
	private String opis;
	
	
	public LiczbaDTO() {}
	
	public LiczbaDTO(long wartosc, String opis) {
		this.wartosc = wartosc;
		this.opis = opis;
	}

}
