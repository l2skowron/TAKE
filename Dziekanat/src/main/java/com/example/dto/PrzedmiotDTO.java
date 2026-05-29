package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

import com.example.entities.Przedmiot;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrzedmiotDTO extends RepresentationModel<PrzedmiotDTO>  {

	private Integer id;
	private String nazwa;
	private Integer ECTS;
	private Integer NumerSemestru;
	private String kierunek;
	public PrzedmiotDTO(){}
	
	public PrzedmiotDTO(Przedmiot przedmiot){
		this.id=przedmiot.getId();
		this.nazwa=przedmiot.getNazwa();
		this.ECTS=przedmiot.getECTS();
		this.NumerSemestru=przedmiot.getNumerSemestru();
		this.kierunek=przedmiot.getKierunek();
	}
}
