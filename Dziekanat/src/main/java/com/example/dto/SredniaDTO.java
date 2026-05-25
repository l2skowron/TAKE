package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SredniaDTO extends RepresentationModel<SredniaDTO>{

	private Integer studentId;
	private Double srednia;
	
	public SredniaDTO(Integer studendId,Double Srednia) {
		this.studentId = studentId;
		this.srednia = srednia;
	}
}
