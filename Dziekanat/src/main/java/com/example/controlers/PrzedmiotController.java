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

import com.example.Repositories.PrzedmiotRepository;
import com.example.main.Przedmiot;

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
@GetMapping("{/id}")
public @ResponseBody Optional<Przedmiot> getOcena(@RequestBody Integer id){
	return przedmiotRepo.findById(id);
}
@GetMapping("/{get}")
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
}
