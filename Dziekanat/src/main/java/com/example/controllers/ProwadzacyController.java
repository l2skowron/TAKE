package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.dto.LiczbaDTO;
import com.example.dto.OcenaDTO;
import com.example.dto.ProwadzacyDTO;
import com.example.dto.StudentDTO;
import com.example.entities.Ocena;
import com.example.entities.Prowadzacy;
import com.example.entities.Przedmiot;
import com.example.entities.Student;
import com.example.repositories.ProwadzacyRepository;
import com.example.repositories.PrzedmiotRepository;

@Controller
@RequestMapping("/prowadzacy")
public class ProwadzacyController {
	
	@Autowired
	ProwadzacyRepository prowadzacyRepo;
	
@PostMapping
	public @ResponseBody String addProwadzacy(@RequestBody Prowadzacy prowadzacy) {
	prowadzacy = prowadzacyRepo.save(prowadzacy);
		return "Added with id " + prowadzacy.getId();	
	}
@GetMapping("/{id}")
public @ResponseBody ProwadzacyDTO getById(@PathVariable Integer id){
	Prowadzacy prowadzacy = prowadzacyRepo.findById(id).orElse(null);
	if(prowadzacy ==null)
	{
		return null;
	}
	
	return new ProwadzacyDTO(prowadzacy);
}
@GetMapping
public @ResponseBody Iterable<Prowadzacy> getAll(){
	return prowadzacyRepo.findAll();
	}
@PutMapping
public @ResponseBody String updateProwadzacy(@RequestBody Prowadzacy prowadzacy) {
	if(prowadzacy.getId()==null) {
		return "Podaj id. ";
	}
	prowadzacyRepo.save(prowadzacy);
	return "Zaktualizowano prowadzacego o id= " + prowadzacy.getId();
}

@DeleteMapping("/{id}")
public @ResponseBody String delete(@PathVariable Integer id) {
	 prowadzacyRepo.deleteById(id);
	return "Usunieto prowadzącego o id " + id;
}

@Autowired
private PrzedmiotRepository przedmiotRepo;
@GetMapping("/{id}/oceny")
public @ResponseBody CollectionModel<OcenaDTO> getOcenyForProwadzacy(@PathVariable Integer id){
	Prowadzacy prowadzacy = prowadzacyRepo.findById(id).orElse(null);
	if(prowadzacy == null) return null;
	
	List<OcenaDTO> ocenyDTO =new ArrayList<>();
	
	List<Przedmiot> przedmiotyProwadzacego = przedmiotRepo.findByProwadzacyId(id);
	for (Przedmiot przedmiot  : przedmiotyProwadzacego) {
		if(przedmiot.getOcena() !=null) {
			for(Ocena ocena : przedmiot.getOcena()) {
				ocenyDTO.add(new OcenaDTO(ocena));
			}
		}
	}
	CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(ocenyDTO);
	
	collectionModel.add(linkTo(methodOn(ProwadzacyController.class)
			.getOcenyForProwadzacy(id)).withSelfRel());
	
	return collectionModel;
}


@GetMapping("/liczba")
public @ResponseBody LiczbaDTO getSumaProwadzacych() {
	long iloscProwadzacych = prowadzacyRepo.count();
	
	LiczbaDTO dto = new LiczbaDTO(iloscProwadzacych, "Suma wszystkich prowadzacych" );
	dto.add(linkTo(methodOn(ProwadzacyController.class).getSumaProwadzacych()).withSelfRel());

return dto;
}
@GetMapping("/tytulNaukowy")
public @ResponseBody CollectionModel<ProwadzacyDTO> getProwadzacyByTytulNaukowy(
		@RequestParam String tytul){
	
	
	List<Prowadzacy> prowadzacy = prowadzacyRepo.findByTytulNaukowy(tytul);
	List<ProwadzacyDTO> dtos = new ArrayList<>();
	for(Prowadzacy p : prowadzacy) {
		dtos.add(new ProwadzacyDTO(p));	
	}
	
CollectionModel<ProwadzacyDTO> collectionModel = CollectionModel.of(dtos);
	
	collectionModel.add(linkTo(methodOn(ProwadzacyController.class)
			.getProwadzacyByTytulNaukowy(tytul)).withSelfRel());
	
	return collectionModel;
	}
@GetMapping("/katedra")
public @ResponseBody CollectionModel<ProwadzacyDTO> getProwadzacyByKatedra(
		@RequestParam String katedra){
	
	List<Prowadzacy> prowadzacy = prowadzacyRepo.findByKatedra(katedra);
	List<ProwadzacyDTO> dtos = new ArrayList<>();
	
	for(Prowadzacy p : prowadzacy) {
		dtos.add(new ProwadzacyDTO(p));
	}
	
	CollectionModel<ProwadzacyDTO> collectionModel = CollectionModel.of(dtos);
	
	collectionModel.add(linkTo(methodOn(ProwadzacyController.class)
			.getProwadzacyByKatedra(katedra)).withSelfRel());
return collectionModel;
}
	

}
