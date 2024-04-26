package com.example.atenciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.atenciones.model.HistorialMedico;
import com.example.atenciones.model.Paciente;
import com.example.atenciones.repository.PacienteRepository;
import com.example.atenciones.repository.HistorialMedicoRepository;



import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    @Override
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> getPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente createPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    @Override
    public Paciente updatePaciente(Long id, Paciente paciente) {
        if( pacienteRepository.existsById(id)){
            paciente.setId(id);
            return pacienteRepository.save(paciente);
        }else{
            return null;
        }
    }

    @Override
    public void deletePaciente(Long id) {
        pacienteRepository.deleteById(id);
    }


    //Historial Médico

    @Override
    public HistorialMedico createHistorialMedico(Long pacienteId, HistorialMedico historial) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            historial.setPaciente(paciente); 
            
            return historialMedicoRepository.save(historial); 
        } else {
            return null; 
        }
    }

}
