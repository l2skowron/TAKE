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

import com.example.Repositories.ProwadzacyRepo;
import com.example.main.Prowadzacy;

@Controller
@RequestMapping("/prowadzacy")
public class ProwadzacyController {
	
	@Autowired
	ProwadzacyRepo prowadzacyRepo;
	
@PostMapping
	public @ResponseBody String addProwadzacy(@RequestBody Prowadzacy prowadzacy) {
	prowadzacy = prowadzacyRepo.save(prowadzacy);
		return "Added with id " + prowadzacy.getId();	
	}
@GetMapping("/{id}")
public @ResponseBody Optional<Prowadzacy> getProwadzacy(@RequestBody Integer id){
	return prowadzacyRepo.findById(id);
}
@GetMapping("/{get}")
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


}
