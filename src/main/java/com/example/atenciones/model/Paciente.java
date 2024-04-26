package com.example.atenciones.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente extends RepresentationModel<Paciente> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "rut")
    private String rut;

    @Column(name= "edad")
    private Integer edad; 

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<HistorialMedico> historialesMedicos = new ArrayList<>();

    //Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRut() {
        return rut;
    }

    public Integer getEdad() {
        return edad;
    }

    public List<HistorialMedico> getHistorialesMedicos() {
        return historialesMedicos;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRut(String rut){
        this.rut = rut;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setHistorialesMedicos(List<HistorialMedico> historialesMedicos) {
        this.historialesMedicos = historialesMedicos;
    }


}