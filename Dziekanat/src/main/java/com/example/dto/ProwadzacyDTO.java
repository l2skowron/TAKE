package com.example.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import com.example.controllers.ProwadzacyController;
import com.example.entities.Prowadzacy;
public class ProwadzacyDTO extends RepresentationModel<ProwadzacyDTO> {
	
		private Integer Id;
		private Integer pesel;
		private String imie;
		private String nazwisko;
		private String email;
		private String katedra;
		private String wydzial;
		private String tytulNaukowy;
		
		public ProwadzacyDTO() {}
		
		public ProwadzacyDTO(Prowadzacy prowadzacy) {
			this.Id = prowadzacy.getId();
			this.pesel = prowadzacy.getPesel();
			this.imie = prowadzacy.getImie();
			this.nazwisko = prowadzacy.getNazwisko();
			this.email = prowadzacy.getEmail();
			this.katedra = prowadzacy.getKatedra();
			this.wydzial = prowadzacy.getWydzial();
			this.tytulNaukowy = prowadzacy.getTytulNaukowy();
			
			
			this.add(linkTo(methodOn(ProwadzacyController.class).getProwadzacy(prowadzacy.getId())).withRel("Prowadzacy"));

		}
		
		
}
