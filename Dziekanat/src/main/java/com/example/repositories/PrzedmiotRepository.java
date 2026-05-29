package com.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entities.Przedmiot;

public interface PrzedmiotRepository extends CrudRepository<Przedmiot,Integer> {
	@Query("SELECT DISTINCT pr FROM Prowadzacy po JOIN po.ocena o JOIN o.przedmiot pr WHERE po.id = :prowadzacyId")
    List<Przedmiot> findByProwadzacyId(@Param("prowadzacyId") Integer prowadzacyId);
	List<Przedmiot> findTop5ByOrderByECTSDesc();
	List<Przedmiot> findPrzedmiotByECTS(Integer ECTS);
}
