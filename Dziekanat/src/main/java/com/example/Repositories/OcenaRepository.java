package com.example.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.main.Ocena;

public interface OcenaRepository extends CrudRepository<Ocena,Integer> {

	List<Ocena> findByProwadzacyId(Integer prowadzacyId);
	List<Ocena> findByDataWystawieniaBetween(LocalDate dataPoczatkowa, LocalDate dataKoncowa);
}
