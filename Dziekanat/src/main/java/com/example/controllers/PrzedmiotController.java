package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.example.dto.ProwadzacyDTO;
import com.example.dto.PrzedmiotDTO;
import com.example.entities.Ocena;
import com.example.entities.Prowadzacy;
import com.example.entities.Przedmiot;
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
	return "Dodano ocene o id" + przedmiot.getId();
}
@GetMapping("/{id}")
public @ResponseBody Optional<Przedmiot> getPrzedmiot(@PathVariable Integer id){
	return przedmiotRepo.findById(id);
}
@GetMapping
public @ResponseBody Iterable<Przedmiot> getAll(){
return przedmiotRepo.findAll();
}
@PutMapping
public @ResponseBody String updatePrzedmiot(@RequestBody Przedmiot przedmiot) {
if(przedmiot.getId()==null) {
	return "Podaj id aby edytowac. ";
}
przedmiotRepo.save(przedmiot);
return "Edytowano ocene o id= " + przedmiot.getId();
}
@DeleteMapping
public @ResponseBody String delete(@PathVariable Integer id) {
przedmiotRepo.deleteById(id);
return "Usunieto ocene o id= "+ id;
}
@Autowired
private OcenaRepository ocenaRepo;

@GetMapping("/{id}/prowadzacy")
	public @ResponseBody CollectionModel<ProwadzacyDTO> getProwadzacyOfPrzedmiot(
			@PathVariable Integer id){
	Przedmiot przedmiot = przedmiotRepo.findById(id).orElse(null);
	
	if(przedmiot == null || przedmiot.getOcena() == null) return null;
	
	Set<Prowadzacy>  unikalniProwadzacy = new HashSet<>();
	
	for(Ocena ocena : przedmiot.getOcena()) {
		unikalniProwadzacy.add(ocena.getProwadzacy());
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
}
