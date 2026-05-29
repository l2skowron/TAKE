package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
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

import com.example.dto.OcenaDTO;
import com.example.entities.Ocena;
import com.example.repositories.OcenaRepository;


@Controller
@RequestMapping("/ocena")
public class OcenaController {
	@Autowired
	OcenaRepository ocenaRepo;
@PostMapping
	public @ResponseBody String addOcena(@RequestBody Ocena ocena) {
		ocena = ocenaRepo.save(ocena);
		return "Dodano ocene o id" + ocena.getId();
	}
@GetMapping("/{id}")
	public @ResponseBody Optional<Ocena> getOcena(@PathVariable Integer id){
		return ocenaRepo.findById(id);
	}
@GetMapping
	public @ResponseBody Iterable<Ocena> getAll(){
	return ocenaRepo.findAll();
	}
@PutMapping
public @ResponseBody String updateOcena(@RequestBody Ocena ocena) {
	if(ocena.getId()==null) {
		return "Podaj id aby edytowac. ";
	}
	ocenaRepo.save(ocena);
	return "Edytowano ocene o id= " + ocena.getId();
}
@DeleteMapping
public @ResponseBody String delete(@PathVariable Integer id) {
	ocenaRepo.deleteById(id);
	return "Usunieto ocene o id= "+ id;
}
@GetMapping("/ocenaPrzedzial")
public @ResponseBody CollectionModel<OcenaDTO> findByDataWystawieniaBetween(
		@RequestParam LocalDate dataPoczatkowa, @RequestParam LocalDate dataKoncowa){
	List<Ocena> ocenyWPrzedziale  = ocenaRepo.findByDataBetween(dataPoczatkowa,dataKoncowa);

	List<OcenaDTO> dtos = new ArrayList<>();

	for(Ocena o : ocenyWPrzedziale) {
		if(o.getPrzedmiot()!=null) {
		dtos.add(new OcenaDTO(o));
		}
	}
	CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(OcenaController.class)
            .findByDataWystawieniaBetween(dataPoczatkowa, dataKoncowa)).withSelfRel());
	return collectionModel;
}
@GetMapping("/{id}/ocena")
public @ResponseBody OcenaDTO getOcenaById(@PathVariable Integer id) {
	
	Ocena ocena = ocenaRepo.findById(id).orElse(null);
	if (ocena == null) return null;
	
	OcenaDTO dto = new OcenaDTO(ocena);
	
	
	dto.add(linkTo(methodOn(OcenaController.class).getOcenaById(id)).withSelfRel());
	
	return dto;
}
}
