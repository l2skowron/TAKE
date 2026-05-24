package com.example.main;

import java.security.Timestamp;

import org.springframework.stereotype.Component;

import com.example.enums.TypZaliczenia;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private Integer id;
	private Integer wartosc;
	private Timestamp data;
	private TypZaliczenia typZaliczenia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="prowadzacy_id")
	private Prowadzacy prowadzacy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="przedmiot_id")
	private Przedmiot przedmiot;
}