package com.example.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.main.Prowadzacy;
import java.util.List;

public interface ProwadzacyRepo extends CrudRepository<Prowadzacy, Integer>{
	
	List<Prowadzacy> findByTytulNaukowy(String tytulNaukowy);
	List<Prowadzacy> findByKatedra(String Katedra);
}
