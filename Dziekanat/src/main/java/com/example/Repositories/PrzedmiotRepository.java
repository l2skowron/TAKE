package com.example.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.main.Przedmiot;
import java.util.List;

public interface PrzedmiotRepository extends CrudRepository<Przedmiot,Integer> {
	
	List<Przedmiot> findByProwadzacyId(Integer prowadzacyId);
	List<Przedmiot> findTop5ByOrderByECTSDesc();
	List<Przedmiot> findPrzedmiotByECTS(Integer ECTS);
}
