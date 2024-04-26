package com.example.atenciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.atenciones.model.Paciente;
import com.example.atenciones.repository.PacienteRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {
    
    @InjectMocks
    private PacienteServiceImpl pacienteServicio;

    @Mock
    private PacienteRepository pacienteRepositoryMock;

    @Test
    public void guardarPacienteTest() {

        // Creación de un objeto Paciente para utilizar como datos de prueba
        Paciente paciente = new Paciente();
        paciente.setName("Carlos Fabres");

        // Configuración del comportamiento del mock del repositorio para que al llamar a save() devuelva el paciente creado
        when(pacienteRepositoryMock.save(any())).thenReturn(paciente);

        // Llamada al método bajo prueba para crear un paciente
        Paciente resultado = pacienteServicio.createPaciente(paciente);

        // Verificación: Se asegura de que el nombre del paciente creado coincide con el nombre especificado
        assertEquals("Carlos Fabres", resultado.getName());
    }

    @Test
    public void getPacienteByIdTest() {
        // Datos de prueba: ID y nombre de paciente
        Long pacienteId = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        paciente.setName("Carlos Fabres");

        // Configuración del mock del repositorio para que al llamar a findById() con cualquier Long devuelva el paciente creado
        when(pacienteRepositoryMock.findById(anyLong())).thenReturn(Optional.of(paciente));

        // Llamada al método bajo prueba para obtener un paciente por su ID
        Optional<Paciente> resultado = pacienteServicio.getPacienteById(pacienteId);

        // Verificación: Se asegura de que el nombre del paciente obtenido coincide con el nombre especificado
        assertEquals("Carlos Fabres", resultado.get().getName());
    }
}
