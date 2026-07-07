package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.example.error.InvalidRequestException;
import com.example.error.ResourceNotFoundException;
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
public @ResponseBody OcenaDTO getById(@PathVariable Integer id){
	Ocena ocena = ocenaRepo.findById(id).orElse(null);
	if(ocena ==null)
	{
		throw new ResourceNotFoundException("Ocena o id: "+ id+ " nie istnieje. ");
	}
	
	return new OcenaDTO(ocena);
}
@GetMapping
	public @ResponseBody Iterable<Ocena> getAll(){
	return ocenaRepo.findAll();
	}
@PutMapping
public @ResponseBody String updateOcena(@RequestBody Ocena ocena) {
	if(ocena.getId()==null) {
		throw new InvalidRequestException("Podaj id aby edytowac. ");
	}
	if(!ocenaRepo.existsById(ocena.getId())) {
		throw new ResourceNotFoundException("Ocena o id: "+ ocena.getId()+ " nie istnieje. ");
	}
	ocenaRepo.save(ocena);
	return "Edytowano ocene o id= " + ocena.getId();
}
@DeleteMapping("/{id}")
public @ResponseBody String delete(@PathVariable Integer id) {
	if(!ocenaRepo.existsById(id)) {
		throw new ResourceNotFoundException("Ocena o id: "+ id+ " nie istnieje. ");
	}
	ocenaRepo.deleteById(id);
	return "Usunieto ocene o id= "+ id;
}
@GetMapping("/ocenaPrzedzial")
public @ResponseBody CollectionModel<OcenaDTO> findByDataWystawieniaBetween(
		@RequestParam LocalDate dataPoczatkowa, @RequestParam LocalDate dataKoncowa){
	List<Ocena> ocenyWPrzedziale  = ocenaRepo.findByDataBetween(dataPoczatkowa,dataKoncowa);
	if(ocenyWPrzedziale.isEmpty()) {
		throw new ResourceNotFoundException("Nie znaleziono ocen dla podanego przedziału");
	}

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
@GetMapping("/{id}/ocena-szczegoly")
public @ResponseBody OcenaDTO getOcenaById(@PathVariable Integer id) {
	
	Ocena ocena = ocenaRepo.findById(id).orElse(null);
	if (ocena == null) 
		throw new ResourceNotFoundException("Ocena o id: "+ id+ " nie istnieje. ");
	
	OcenaDTO dto = new OcenaDTO(ocena);
	
	
	dto.add(linkTo(methodOn(OcenaController.class).getOcenaById(id)).withSelfRel());
	dto.add(linkTo(methodOn(StudentController.class).getById(ocena.getStudent().getId())).withRel("student"));
	dto.add(linkTo(methodOn(ProwadzacyController.class).getById(ocena.getProwadzacy().getId())).withRel("prowadzacy"));
	dto.add(linkTo(methodOn(PrzedmiotController.class).getById(ocena.getPrzedmiot().getId())).withRel("przedmiot"));
	
	return dto;
}
@GetMapping("/{id}/prowadzacy")
public @ResponseBody CollectionModel<OcenaDTO> getOcenaByProwadzacy(@PathVariable Integer id) {
	
	List<Ocena> ocenyProwadzacego = ocenaRepo.findByProwadzacyId(id);
	if(ocenyProwadzacego.isEmpty()) {
		throw new ResourceNotFoundException("Nie znaleziono ocen dla prowadzącego o id: " + id);
	}
	
	List<OcenaDTO> dtos = new ArrayList<>();
	for(Ocena o : ocenyProwadzacego) {
		if(o != null) {
		dtos.add(new OcenaDTO(o));
	}
		}
	CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(OcenaController.class)
            .getOcenaByProwadzacy(id)).withSelfRel());
	return collectionModel;
}
}
