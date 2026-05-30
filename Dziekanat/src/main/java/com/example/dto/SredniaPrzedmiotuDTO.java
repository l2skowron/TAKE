package com.example.dto;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SredniaPrzedmiotuDTO extends RepresentationModel<SredniaPrzedmiotuDTO>{

		private Integer przedmiotId;
		private String nazwaPrzedmiotu;
		private Double srednia;
		
		public SredniaPrzedmiotuDTO() {}
		
		public SredniaPrzedmiotuDTO(Integer przedmiotId,String nazwaPrzedmiotu, Double srednia) {
			this.przedmiotId = przedmiotId;
			this.nazwaPrzedmiotu = nazwaPrzedmiotu;
			this.srednia = (srednia != null) ? Math.round(srednia * 100.0) / 100.0 : 0.0;
		}
	}


