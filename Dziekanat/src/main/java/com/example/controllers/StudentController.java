package com.example.controllers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.example.dto.LiczbaDTO;
import com.example.dto.OcenaDTO;
import com.example.dto.PrzedmiotDTO;
import com.example.dto.RankingDTO;
import com.example.dto.SredniaDTO;
import com.example.dto.StudentDTO;
import com.example.entities.Ocena;
import com.example.entities.Przedmiot;
import com.example.entities.Student;
import com.example.repositories.OcenaRepository;
import com.example.repositories.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {

	
	
	private double sredniaDoOcena(double srednia) {
		if(srednia<2.75)return 2.0;
		if(srednia<3.25)return 3.0;
		if(srednia<3.75)return 3.5;
		if(srednia<4.25)return 4.0;
		if(srednia<4.75)return 4.5;
		return 5.0;
	}
	
	@Autowired
	StudentRepository studentRepo;
	@PostMapping
	public @ResponseBody String addStudent(@RequestBody Student student) {
		student= studentRepo.save(student);
		return "Added with id=" + student.getId();
	}
		
	@GetMapping("/{id}")
	public @ResponseBody StudentDTO getById(@PathVariable Integer id){
		Student student = studentRepo.findById(id).orElse(null);
		if(student ==null)
		{
			return null;
		}
		
		return new StudentDTO(student);
	}
	@GetMapping
	public @ResponseBody Iterable<Student> getAll(){
		return studentRepo.findAll();
	}
	@PutMapping
	public @ResponseBody String editStudent(@RequestBody Student student) {
		if(student.getId()==null) {
			return "Podaj id aby edytowac. ";
		}
		studentRepo.save(student);
		return "Zaktualizowano dane studenta o id= " + student.getId();
		}
	
	@DeleteMapping("/{id}")
	public @ResponseBody String delete(@PathVariable Integer id) {
		studentRepo.deleteById(id);
		return "Usunięto studenta o id=" +id;	
	}
	
	@GetMapping("/szukaj")
	public @ResponseBody CollectionModel<StudentDTO> searchStudents(
			@RequestParam(required = false) String kierunek,
	        @RequestParam(required = false) Integer semestr,
	        @RequestParam(required = false) String nazwisko) {
		
		List<Student> repoStudent;
		
		if(kierunek != null && semestr != null) {
			repoStudent = studentRepo.findByKierunekAndSemestr(kierunek,semestr);
		}else if(kierunek != null) {
			repoStudent = studentRepo.findByKierunek(kierunek);
		}else if(semestr != null) {
			repoStudent = studentRepo.findBySemestr(semestr);
		}else {
			repoStudent=(List<Student>) studentRepo.findAll();
		}
	
	
	List<StudentDTO> dtos = new ArrayList<>();
	for(Student student : repoStudent) {
		dtos.add(new StudentDTO(student));
	}
	
	CollectionModel<StudentDTO> collectionModel = CollectionModel.of(dtos);
	
	collectionModel.add(linkTo(methodOn(StudentController.class)
			.searchStudents(kierunek,semestr,nazwisko)).withSelfRel());
	
	return collectionModel;
}
	@GetMapping("/{id}/oceny")
	public @ResponseBody CollectionModel<OcenaDTO> getOcenyForStudent(@PathVariable Integer id){
		Student student = studentRepo.findById(id).orElse(null);
		if(student == null) return null;
		
		List<OcenaDTO> ocenyDTO =new ArrayList<>();
		for(Ocena ocena : student.getOceny()) {
			ocenyDTO.add(new OcenaDTO(ocena));
		}
		CollectionModel<OcenaDTO> collectionModel = CollectionModel.of(ocenyDTO);
		
		collectionModel.add(linkTo(methodOn(StudentController.class).getOcenyForStudent(id)).withSelfRel());
		
		return collectionModel;
	}
	
	@GetMapping("/{id}/srednia/przedmiot/{przedmiotId}")
	public @ResponseBody SredniaDTO getSredniaForPrzedmiot(@PathVariable Integer id,
			@PathVariable Integer przedmiotId){
		Student student = studentRepo.findById(id).orElse(null);
		if(student == null || student.getOceny() == null || student.getOceny().isEmpty()) {
			return new SredniaDTO(id,0.0);
		}
		double sumaIloczynow = 0;
		double sumaWag = 0;
		for(Ocena ocena : student.getOceny()) {
			if(ocena.getPrzedmiot()!= null && ocena.getPrzedmiot().getId().equals(przedmiotId)) {
			double wartoscOceny = ocena.getWartosc();
			double wagaOceny = ocena.getTypZaliczenia().getPoziom();
			sumaIloczynow += (wartoscOceny*wagaOceny);
			sumaWag += wagaOceny;
			}
		}
	double srednia = 0.0;
	if(sumaWag>0) {
		srednia = sumaIloczynow/sumaWag;
		srednia = Math.round(srednia*100.0)/100.0;
	}
	SredniaDTO dto = new SredniaDTO(id,srednia);
	dto.add(linkTo(methodOn(StudentController.class).getSredniaForPrzedmiot(id, przedmiotId)).withSelfRel());
	dto.add(linkTo(methodOn(StudentController.class).getById(id)).withRel("student"));
		
	return dto;
}
	
	@GetMapping("/{id}/srednia-ogolna")
	public @ResponseBody SredniaDTO getSredniaOgolnaECTS(@PathVariable Integer id,
			@RequestParam(required = false) Integer semestr) {
	Student student = studentRepo.findById(id).orElse(null);
	if(student == null || student.getOceny() == null || student.getOceny().isEmpty()) {
		return new SredniaDTO(id,0.0);
	}
	Map<Przedmiot,List<Ocena>> ocenyDlaPrzedmiotu = new HashMap<>();
	for(Ocena ocena : student.getOceny()) {
		Przedmiot p = ocena.getPrzedmiot();
		if(p != null ) {
			
			if(semestr != null &&  !semestr.equals(p.getNumerSemestru())) {
				continue;
			}
			ocenyDlaPrzedmiotu.computeIfAbsent(p,k -> new ArrayList<>()).add(ocena);
		}
	}
	double sumaIloczynow = 0.0;
	double sumaECTS = 0.0;
	
	for (Map.Entry<Przedmiot, List<Ocena>> entry : ocenyDlaPrzedmiotu.entrySet()) {
		Przedmiot przedmiot = entry.getKey();
		List<Ocena> listaOcenPrzedmiotu = entry.getValue();
		
		double sumaIloczynowPrzedmiotu = 0.0;
		double sumaWagOcenPrzedmiotu = 0.0;
		for(Ocena o : listaOcenPrzedmiotu) {
			sumaIloczynowPrzedmiotu +=(o.getWartosc()*o.getTypZaliczenia().getPoziom());
			sumaWagOcenPrzedmiotu +=o.getTypZaliczenia().getPoziom();
		}
		
		if(sumaWagOcenPrzedmiotu>0) {
			double sredniaKoncowa = sumaIloczynowPrzedmiotu/sumaWagOcenPrzedmiotu;
			double ocenaKoncowa = sredniaDoOcena(sredniaKoncowa);
			
			double punktyECTS = przedmiot.getECTS();
			sumaIloczynow += (ocenaKoncowa*punktyECTS);
			sumaECTS += punktyECTS;
		}
	}
	double sredniaOgolna = 0.0;
	if(sumaECTS>0) {
		sredniaOgolna = sumaIloczynow/sumaECTS;
		sredniaOgolna = Math.round(sredniaOgolna*100.0)/100.0;
	}
	
	SredniaDTO dto = new SredniaDTO(id,sredniaOgolna);
	dto.add(linkTo(methodOn(StudentController.class).getSredniaOgolnaECTS(id,semestr)).withSelfRel());
	dto.add(linkTo(methodOn(StudentController.class).getById(id)).withRel("student"));
	return dto;
	}


@GetMapping("/{id}/przedmioty-studenta")
public @ResponseBody CollectionModel<PrzedmiotDTO> getPrzedmiotStudent(@PathVariable Integer id) {
	Student student = studentRepo.findById(id).orElse(null);
	if(student == null || student.getOceny() == null) return null;
	
	Set<Przedmiot> przedmioty =new HashSet<>();
	for(Ocena ocena : student.getOceny()) {
		if(ocena.getPrzedmiot()!= null) {
		przedmioty.add(ocena.getPrzedmiot());
		}
		}
	
	List<PrzedmiotDTO> przedmiotyDTO = new ArrayList<>();
	for(Przedmiot przedmiot : przedmioty) {
		przedmiotyDTO.add(new PrzedmiotDTO(przedmiot));
	}
	CollectionModel<PrzedmiotDTO> collectionModel = CollectionModel.of(przedmiotyDTO);
	
	collectionModel.add(linkTo(methodOn(StudentController.class)
			.getPrzedmiotStudent(id)).withSelfRel());
	
	return collectionModel;
}
@GetMapping("/brakOcen")
public @ResponseBody CollectionModel<StudentDTO> getStudentWithoutOceny(){
List<Student> skresleniStudenci  = studentRepo.findStudentsWithoutOceny();

List<StudentDTO> dtos = new ArrayList<>();

for(Student s : skresleniStudenci) {
	dtos.add(new StudentDTO(s));
}
CollectionModel<StudentDTO> collectionModel = CollectionModel.of(dtos);

collectionModel.add(linkTo(methodOn(StudentController.class).getStudentWithoutOceny()).withSelfRel());
return collectionModel;
}

@GetMapping("/liczbaStudentowNaKirunku")
public @ResponseBody LiczbaDTO countByKierunek(
		@RequestParam String kierunek) {
	
	long liczbaStudentow = studentRepo.countByKierunek(kierunek);
	
	LiczbaDTO dto = new LiczbaDTO(liczbaStudentow,"Liczba studentow na kierunku");
	dto.add(linkTo(methodOn(StudentController.class).countByKierunek(kierunek)).withSelfRel());
	return dto;
}

@Autowired
OcenaRepository ocenaRepo;

@GetMapping("/ranking")
public @ResponseBody CollectionModel<RankingDTO> getTopStudenciBySrednia(@RequestParam Integer semestr){
	List<Object[]> sredniaStudent = ocenaRepo.getStudentRankingBySemestr(semestr);
	List<RankingDTO> dtos = new ArrayList<>();
	
	for( Object[] w : sredniaStudent) {
		Student student = (Student) w[0];
		Double srednia = (Double) w[1];
		dtos.add(new RankingDTO(student.getId(), student.getImie(), student.getNazwisko(), srednia));
	}
	CollectionModel<RankingDTO> collectionModel = CollectionModel.of(dtos);
	collectionModel.add(linkTo(methodOn(StudentController.class).getTopStudenciBySrednia(semestr)).withSelfRel());
	
	return collectionModel;
	
}
}

	
		
