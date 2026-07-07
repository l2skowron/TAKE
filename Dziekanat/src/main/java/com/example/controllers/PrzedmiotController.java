package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.OcenaDTO;
import com.example.dto.ProwadzacyDTO;
import com.example.dto.PrzedmiotDTO;
import com.example.dto.SredniaPrzedmiotuDTO;
import com.example.entities.Ocena;
import com.example.entities.Prowadzacy;
import com.example.entities.Przedmiot;
import com.example.error.InvalidRequestException;
import com.example.error.ResourceNotFoundException;
import com.example.repositories.OcenaRepository;
import com.example.repositories.PrzedmiotRepository;

@Controller
@RequestMapping("/przedmiot")
public class PrzedmiotController {
@Autowired
PrzedmiotRepository przedmiotRepo;

@PostMapping
public @ResponseBody String addPrzedmiot(@RequestBody Przedmiot przedmiot) {
	przedmiot = przedmiotRepo.save(przedmiot);
	return "Dodano przedmiot o id" + przedmiot.getId();
}
@GetMapping("/{id}")
public @ResponseBody PrzedmiotDTO getById(@PathVariable Integer id){
	Przedmiot przedmiot = przedmiotRepo.findById(id).orElse(null);
	if(przedmiot ==null)
	{
		throw new ResourceNotFoundException("Przedmiot o id: " + id + " nie istnieje.");
	}
	
	return new PrzedmiotDTO(przedmiot);
}
@GetMapping
public @ResponseBody Iterable<Przedmiot> getAll(){
return przedmiotRepo.findAll();
}
@PutMapping
public @ResponseBody String updatePrzedmiot(@RequestBody Przedmiot przedmiot) {
if(przedmiot.getId()==null) {
	throw new InvalidRequestException("Podaj id aby edytować");
}
if(!przedmiotRepo.existsById(przedmiot.getId())) {
	throw new ResourceNotFoundException("Przedmiot o id: "+ przedmiot.getId() + " nie istnieje.");
}
przedmiotRepo.save(przedmiot);
return "Edytowano ocene o id= " + przedmiot.getId();
}
@DeleteMapping("/{id}")
public @ResponseBody String delete(@PathVariable Integer id) {
	if(!przedmiotRepo.existsById(id)) {
		throw new ResourceNotFoundException("Przedmiot o id: "+ id + " nie istnieje.");
	}
przedmiotRepo.deleteById(id);
return "Usunieto ocene o id= "+ id;
}
@Autowired
private OcenaRepository ocenaRepo;

@GetMapping("/{id}/prowadzacy")
	public @ResponseBody CollectionModel<ProwadzacyDTO> getProwadzacyOfPrzedmiot(
			@PathVariable Integer id){
	Przedmiot przedmiot = przedmiotRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono przedmiotu o ID: " + id ));
	
	if(przedmiot == null || przedmiot.getOcena() == null) return null;
	
	Set<Prowadzacy>  unikalniProwadzacy = new HashSet<>();
	if(przedmiot.getOcena()!=null) {
	for(Ocena ocena : przedmiot.getOcena()) {
		if(ocena != null && ocena.getProwadzacy() != null) {
		unikalniProwadzacy.add(ocena.getProwadzacy());
			}
		}
	}
	List<ProwadzacyDTO> dtos = new ArrayList<>();
	
	for(Prowadzacy p : unikalniProwadzacy) {
		dtos.add(new ProwadzacyDTO(p));
	}
	
	CollectionModel<ProwadzacyDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(PrzedmiotController.class)
			.getProwadzacyOfPrzedmiot(id)).withSelfRel());
	
	return collectionModel;
}

@GetMapping("/ects")
public @ResponseBody CollectionModel<PrzedmiotDTO> getPrzedmiotByECTS(
		@RequestParam Integer ECTS){
	if(ECTS>10|| ECTS<0) {
		throw new InvalidRequestException("Podano błędną wartość ECTS.");
	}
	List <Przedmiot> przedmioty = przedmiotRepo.findPrzedmiotByECTS(ECTS);
	
	List<PrzedmiotDTO> dtos = new ArrayList<>();
	
	for(Przedmiot p : przedmioty) {
		dtos.add(new PrzedmiotDTO(p));
	}
	CollectionModel<PrzedmiotDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(PrzedmiotController.class)
			. getPrzedmiotByECTS(ECTS)).withSelfRel());
	return collectionModel;
}

@GetMapping("/top-ects")
public @ResponseBody CollectionModel<PrzedmiotDTO> getTopPrzedmiotByECTS(){
	List <Przedmiot> topPrzedmioty = przedmiotRepo.findTop5ByOrderByECTSDesc();
	
	List<PrzedmiotDTO> dtos = new ArrayList<>();
	for(Przedmiot p : topPrzedmioty) {
		dtos.add(new PrzedmiotDTO(p));
	}
	CollectionModel<PrzedmiotDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(PrzedmiotController.class)
			. getTopPrzedmiotByECTS()).withSelfRel());
	return collectionModel;
	}
@GetMapping("/srednia")
public @ResponseBody SredniaPrzedmiotuDTO getSredniaPrzedmiotu(@RequestParam Integer id) {
	
	Przedmiot przedmiot = przedmiotRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono przedmiotu o ID: " + id ));
	
	Double srednia = ocenaRepo.getSredniaOcenPrzedmiotu(id);
	
	SredniaPrzedmiotuDTO dto = new SredniaPrzedmiotuDTO(id, przedmiot.getNazwa(), srednia);
	dto.add(linkTo(PrzedmiotController.class)
			.slash("srednia")
			.withSelfRel());
	return dto;
}
@GetMapping("/oceny")
public @ResponseBody CollectionModel<OcenaDTO> getOcenyForPrzedmiot(@RequestParam Integer id) {
	Przedmiot przedmiot = przedmiotRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono przedmiotu o ID: " + id ));
	
	List<OcenaDTO> ocenyDTO =new ArrayList<>();
	for(Ocena ocena : przedmiot.getOcena()) {
		ocenyDTO.add(new OcenaDTO(ocena));
	}
	CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(ocenyDTO);
	
	collectionModel.add(linkTo(methodOn(PrzedmiotController.class).getOcenyForPrzedmiot(id)).withSelfRel());
	
	return collectionModel;
}

}


