package com.example.entities;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.enums.TypZaliczenia;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
@Entity
public class Ocena {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer wartosc;
	private LocalDate data;
	private TypZaliczenia typZaliczenia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="student_id")
	@JsonIgnore
	private Student student;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="prowadzacy_id")
	@JsonIgnore
	private Prowadzacy prowadzacy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="przedmiot_id")
	@JsonIgnore
	private Przedmiot przedmiot;
}