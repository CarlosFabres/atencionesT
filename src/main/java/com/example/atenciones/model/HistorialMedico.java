package com.example.atenciones.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "fecha_atencion")
    private LocalDate fechaAtencion;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "doctor")
    private String doctor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private Paciente paciente;

    // Getters
    public Integer getId() {
        return id;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDoctor() {
        return doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}


