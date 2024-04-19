package com.curso.javaspringboot.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.curso.javaspringboot.entitys.Cliente;
import com.curso.javaspringboot.services.IClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK) // 200 get
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {

			cliente = clienteService.findById(id);

		}

		// capturar el erro del servidor
		catch (DataAccessException e) {
			response.put("mensaje", "eror al realizar la consula en la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		// capturar el error del id
		if (cliente == null) {
			response.put("mensaje", "El cliente ID :".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}

	// -------------------------
	// @valid si no pongo aqui la anotacion valid aunque tenga las reglas en la
	// clase entity no se validaran estas
	// tambien inyectamos en el controlador el mensaje de error con binding resul
	// -------------
	@PostMapping("/clientes")
	// 201 create
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {

		Cliente newCliente = null;
		Map<String, Object> response = new HashMap<>();
		// 400 bad request ESTA FORMA ES ANTES DE JAVA 8
		/*
		 * if(bindingResult.hasErrors()){
		 * List<String> errors= new ArrayList<>();
		 * 
		 * for(FieldError error : bindingResult.getFieldErrors())/*
		 * errors.add("el campo "+ error.getField()+ " "+ error.getDefaultMessage());
		 */

		// ESTA FORMA ES DE JAVA 8 O SUPERIOR
		System.out.println("Cliente recibido: " + cliente);
		System.out.println("Errores: " + bindingResult.getAllErrors());
		if (bindingResult.hasErrors()) {
			System.out.println("hola");
			List<String> errors = bindingResult.getFieldErrors()
					.stream()//recorre todo los errores obtenidos
					//funcion de flecha o lambda  err es un objeto de la clase Field ERROR
					.map(err -> "el campo " + err.getField() + " " + err.getDefaultMessage()) // cambiamos la informacion obtenida de cada error
					.collect(Collectors.toList());// crea una nueva lista

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			newCliente = clienteService.save(cliente);

		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar al insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		response.put("mensaje", "el cliente ha sido creado con éxito backend!");
		response.put("cliente", newCliente);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result,@PathVariable Long id) {
		Cliente clienteSinActualizar = clienteService.findById(id);
		Cliente clienteActualizado = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()// recorre todo los errores obtenidos
					// funcion de flecha o lambda err es un objeto de la clase Field ERROR
					.map(err -> "el campo " + err.getField() + " " + err.getDefaultMessage()) // cambiamos la
																								// informacion obtenida
																								// de cada error
					.collect(Collectors.toList());// crea una nueva lista

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (clienteSinActualizar == null) {
			response.put("mensaje", "Error no se pudo editar, el cliente ID "
					.concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteSinActualizar.setApellido(cliente.getApellido());
			clienteSinActualizar.setEmail(cliente.getEmail());
			clienteSinActualizar.setNombre(cliente.getNombre());

			clienteActualizado = clienteService.save(clienteSinActualizar);

		} catch (DataAccessException e) {
			response.put("mensaje", "error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		response.put("mensaje ", "el cliente ha sido actualizado backend");
		response.put("cliente", clienteActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@DeleteMapping("/clientes/{id}")

	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteService.findById(id);
		try {
			if (cliente == null) {
				response.put("mensaje", "Error: no se pudo eliminar, el cliente con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "cliente eliminado con exito !");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
