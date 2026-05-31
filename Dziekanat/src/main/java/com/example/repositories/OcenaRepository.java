package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entities.Ocena;

public interface OcenaRepository extends CrudRepository<Ocena,Integer> {

	List<Ocena> findByProwadzacyId(Integer prowadzacyId);
	List<Ocena> findByDataBetween(LocalDate dataPoczatkowa, LocalDate dataKoncowa);
	@Query("SELECT AVG(o.wartosc) FROM Ocena o WHERE o.przedmiot.id = :przedmiotId")
	Double getSredniaOcenPrzedmiotu(@Param("przedmiotId") Integer id);
	@Query("SELECT o.student, AVG(o.wartosc) FROM Ocena o WHERE o.przedmiot.NumerSemestru = :numerSemestru GROUP BY o.student ORDER BY AVG(o.wartosc) DESC")
	List<Object[]> getStudentRankingBySemestr(@Param("numerSemestru") Integer numerSemestru);
	

}
