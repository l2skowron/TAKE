package com.example.enums;

import lombok.Getter;

@Getter
public enum TypZaliczenia {
Egzamin(5),
Kolokwium(4),
Projekt(3),
Kartkówka(2);

	private final int poziom;
TypZaliczenia(int poziom){
	this.poziom = poziom;
}
}
