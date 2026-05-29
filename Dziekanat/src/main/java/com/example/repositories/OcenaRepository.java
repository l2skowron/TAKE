package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.entities.Ocena;

public interface OcenaRepository extends CrudRepository<Ocena,Integer> {

	List<Ocena> findByProwadzacyId(Integer prowadzacyId);
	List<Ocena> findByDataBetween(LocalDate dataPoczatkowa, LocalDate dataKoncowa);
}
