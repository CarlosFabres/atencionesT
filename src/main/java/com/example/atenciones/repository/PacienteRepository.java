package com.example.atenciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atenciones.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

}
