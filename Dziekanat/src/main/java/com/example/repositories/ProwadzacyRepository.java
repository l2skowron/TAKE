package com.example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.entities.Prowadzacy;

import java.util.List;

public interface ProwadzacyRepository extends CrudRepository<Prowadzacy, Integer>{
	
	List<Prowadzacy> findByTytulNaukowy(String tytulNaukowy);
	List<Prowadzacy> findByKatedra(String Katedra);
}
