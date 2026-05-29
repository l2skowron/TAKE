package com.example.config; // Dostosuj do swojej struktury pakietów

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.entities.Ocena;
import com.example.entities.Prowadzacy;
import com.example.entities.Przedmiot;
import com.example.entities.Student;
import com.example.enums.TypZaliczenia;
import com.example.repositories.OcenaRepository;
import com.example.repositories.ProwadzacyRepository;
import com.example.repositories.PrzedmiotRepository;
import com.example.repositories.StudentRepository;

@Component
public class Dane implements CommandLineRunner {

    private  StudentRepository studentRepo;
    private  ProwadzacyRepository prowadzacyRepo;
    private  PrzedmiotRepository przedmiotRepo;
    private  OcenaRepository ocenaRepo;


    public Dane(StudentRepository studentRepo, ProwadzacyRepository prowadzacyRepo,
                           PrzedmiotRepository przedmiotRepo, OcenaRepository ocenaRepo) {
        this.studentRepo = studentRepo;
        this.prowadzacyRepo = prowadzacyRepo;
        this.przedmiotRepo = przedmiotRepo;
        this.ocenaRepo = ocenaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
       
        if (studentRepo.count() > 0) {
            return; 
        }

        System.out.println("====== INICJALIZACJA PRZYKŁADOWYCH DANYCH W BAZIE ======");

       
        Prowadzacy p1 = new Prowadzacy();
        p1.setNazwisko("Kowalski"); 
        p1.setTytulNaukowy("Profesor");
        p1.setKatedra("Katedra Informatyki");
        prowadzacyRepo.save(p1);

        Prowadzacy p2 = new Prowadzacy();
        p2.setNazwisko("Nowak");
        p2.setTytulNaukowy("Doktor");
        p2.setKatedra("Katedra Matematyki");
        prowadzacyRepo.save(p2);

     
        Przedmiot pr1 = new Przedmiot();
        pr1.setNazwa("Matematyka Dyskretna");
        pr1.setECTS(5);
        pr1.setNumerSemestru(1);
        pr1.setKierunek("Informatyka");
        przedmiotRepo.save(pr1);

        Przedmiot pr2 = new Przedmiot();
        pr2.setNazwa("Programowanie Obiektowe");
        pr2.setECTS(6);
        pr2.setNumerSemestru(2);
        pr2.setKierunek("Informatyka");
        przedmiotRepo.save(pr2);

        Przedmiot pr3 = new Przedmiot();
        pr3.setNazwa("Bazy Danych");
        pr3.setECTS(4);
        pr3.setNumerSemestru(3);
        pr3.setKierunek("Informatyka");
        przedmiotRepo.save(pr3);

        
        Student s1 = new Student();
        s1.setImie("Jan");
        s1.setNazwisko("Nowicki");
        s1.setKierunek("Informatyka");
        studentRepo.save(s1);

        Student s2 = new Student();
        s2.setImie("Anna");
        s2.setNazwisko("Zielińska");
        s2.setKierunek("Informatyka");
        studentRepo.save(s2);

        
        Student s3 = new Student();
        s3.setImie("Piotr");
        s3.setNazwisko("BrakOcen");
        s3.setKierunek("Automatyka");
        studentRepo.save(s3);


       
        Ocena o1 = new Ocena();
        o1.setWartosc(4);
        o1.setTypZaliczenia(TypZaliczenia.Egzamin); 
        o1.setStudent(s1);
        o1.setPrzedmiot(pr1);
        o1.setProwadzacy(p1);
        o1.setData(LocalDate.now()); 
        ocenaRepo.save(o1);


        Ocena o2 = new Ocena();
        o2.setWartosc(3);
        o2.setTypZaliczenia(TypZaliczenia.Kolokwium);
        o2.setStudent(s1);
        o2.setPrzedmiot(pr2);
        o2.setProwadzacy(p2);
        o2.setData(LocalDate.now().minusMonths(1));
        ocenaRepo.save(o2);

        
        Ocena o3 = new Ocena();
        o3.setWartosc(5);
        o3.setTypZaliczenia(TypZaliczenia.Egzamin);
        o3.setStudent(s2);
        o3.setPrzedmiot(pr1);
        o3.setProwadzacy(p1);
        o3.setData(LocalDate.now());
        ocenaRepo.save(o3);

        System.out.println("====== INICJALIZACJA BAZY ZAKOŃCZONA SUKCESEM ======");
    }
}