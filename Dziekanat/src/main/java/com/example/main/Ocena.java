package com.example.main;

import java.security.Timestamp;

import org.springframework.stereotype.Component;

import com.example.enums.TypZaliczenia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

}