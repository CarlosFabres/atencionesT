package com.example.atenciones.service;

import com.example.atenciones.model.HistorialMedico;
import com.example.atenciones.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<Paciente> getAllPacientes();

    Optional<Paciente> getPacienteById(Long id);

    Paciente createPaciente(Paciente paciente);

    Paciente updatePaciente(Long id, Paciente paciente);

    void deletePaciente(Long id);
    
    HistorialMedico createHistorialMedico(Long historialId,HistorialMedico historial);
    

}                                                                         