package com.example.atenciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.example.atenciones.model.HistorialMedico;
import com.example.atenciones.model.Paciente;
import com.example.atenciones.service.PacienteService;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pacientes")
public class Paciente2Controller {
    @Autowired
    private PacienteService pacienteService;

    // EndPoint para obtener a todos los pacientes
    @GetMapping
    public ResponseEntity<Object> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "\"message\": \"No hay pacientes disponibles\"");
        }

        // Convertir la lista de pacientes a EntityModel con enlaces HATEOAS
        List<EntityModel<Paciente>> pacienteModels = pacientes.stream()
                .map(paciente -> {
                    Long pacienteId = paciente.getId();
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Paciente2Controller.class)
                            .getPacienteById(pacienteId.toString())).withSelfRel();
                    return EntityModel.of(paciente, selfLink);
                }).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(pacienteModels));
    }

    // EndPoint para obtener paciente por id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteById(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "\"error\": \"El ID del paciente debe ser mayor que cero\"");
            }
            Optional<Paciente> pacienteOptional = pacienteService.getPacienteById(userId);
            if (pacienteOptional.isPresent()) {
                Paciente paciente = pacienteOptional.get();
                Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Paciente2Controller.class)
                        .getPacienteById(id)).withSelfRel();
                EntityModel<Paciente> pacienteModel = EntityModel.of(paciente, selfLink);
                return ResponseEntity.ok(pacienteModel);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "\"error\": \"El paciente con ID " + userId + " no existe\"");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "\"error\": \"El ID del paciente debe ser un número entero\"");
        }
    }

    // EndPoint para crear un paciente
    @PostMapping
    public ResponseEntity<Object> createPaciente(@RequestBody Paciente paciente) {
        // Verificar si alguno de los campos requeridos está vacío o nulo
        if (paciente.getName() == null || paciente.getRut() == null ||
                paciente.getEdad() == null ||
                paciente.getName().isEmpty() || paciente.getRut().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "\"error\": \"Debes proporcionar todos los campos\"");
        }

        // Si todos los campos necesarios están presentes, llamar al servicio para crear
        // el paciente
        Paciente nuevoPaciente = pacienteService.createPaciente(paciente);

        // Crear un objeto que contenga el mensaje y el paciente
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El paciente se creó correctamente");
        response.put("paciente", nuevoPaciente);

        // Crear enlaces HATEOAS para el paciente creado
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Paciente2Controller.class)
                .getPacienteById(nuevoPaciente.getId().toString())).withSelfRel();

        EntityModel<Paciente> pacienteModel = EntityModel.of(nuevoPaciente, selfLink);

        // Devolver el objeto creado junto con el código de estado 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteModel);
    }

    // EndPoint para actualizar un paciente por id
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        // Verificar si el paciente con el ID proporcionado existe
        Optional<Paciente> pacienteExistenteOptional = pacienteService.getPacienteById(id);
        if (!pacienteExistenteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "\"error\": \"El paciente con ID " + id + " no existe\"");
        }

        // Obtener el paciente existente
        Paciente pacienteExistente = pacienteExistenteOptional.get();

        // Actualizar solo los campos proporcionados en el objeto paciente
        if (paciente.getName() != null && !paciente.getName().isEmpty()) {
            pacienteExistente.setName(paciente.getName());
        }
        if (paciente.getRut() != null && !paciente.getRut().isEmpty()) {
            pacienteExistente.setRut(paciente.getRut());
        }

        // Llamar al servicio para actualizar el paciente
        Paciente pacienteActualizado = pacienteService.updatePaciente(id, pacienteExistente);

        // Crear un objeto que contenga el mensaje y el paciente actualizado
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El paciente se actualizó correctamente");
        response.put("paciente", pacienteActualizado);

        // Crear enlaces HATEOAS para el paciente actualizado
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Paciente2Controller.class)
                .getPacienteById(id.toString())).withSelfRel();

        EntityModel<Paciente> pacienteModel = EntityModel.of(pacienteActualizado, selfLink);

        // Devolver el objeto creado junto con el mensaje
        return ResponseEntity.ok(pacienteModel);
    }

    // EndPoint para eliminar un paciente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable Long id) {

        // Verificar si el paciente con el ID proporcionado existe
        Optional<Paciente> pacienteExistenteOptional = pacienteService.getPacienteById(id);
        if (!pacienteExistenteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "\"error\": \"El paciente con ID " + id + " no existe\"");
        }

        // Llamar al servicio para eliminar el paciente
        pacienteService.deletePaciente(id);

        // Devolver una respuesta exitosa con un mensaje
        return ResponseEntity.ok().body("El paciente con ID " + id + " se eliminó correctamente");

    }

    // Endpoint para crear historiales medicos para los pacientes
    @PostMapping("/{idPaciente}/historial_medico")
    public ResponseEntity<Object> createHistorial(@PathVariable("idPaciente") Long idPaciente,
            @RequestBody HistorialMedico historial) {
        // Intenta crear el pedido utilizando el servicio
        HistorialMedico createdHistorial = pacienteService.createHistorialMedico(idPaciente, historial);

        if (createdHistorial != null) {
            // Si el historial se crea con éxito, devuelve un mensaje de éxito
            return ResponseEntity.ok("Historial Médico creado correctamente");
        } else {
            // Si el Paciente no existe, devuelve un mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El paciente con ID " + idPaciente + " no existe");
        }
    }

}
