package com.example.controlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Repositories.OcenaRepository;
import com.example.main.Ocena;

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
@GetMapping("{/id}")
	public @ResponseBody Optional<Ocena> getOcena(@RequestBody Integer id){
		return ocenaRepo.findById(id);
	}
@GetMapping("/{get}")
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
@Mapping
public @ResponseBody CollectionModel<OcentaDTO> getOcenyForStudents(@PathVariable Integer id){
	Student student = studentRepository.findById(id).orElse(null);
	if(student == null) return null;
	
	List<OcenaDTO> ocenyDTO =new ArrayList<>();
	for(Ocena ocena : student.getOceny()) {
		ocenyDTO.add(new OcenaDTO(ocena));
	}
	CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(OcenyDTO);
	
	collectionModel.add(linkTo(methodOn(StudentController.class).getOcenyForStudent(id)).withSelfRel());
	
	return collectionModel;
}
}
