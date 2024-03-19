package com.curso.javaspringboot.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED) // 201 create
	public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {

		return  ResponseEntity.ok().body(clienteService.save(cliente));

	}

	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> update(@Valid @RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteupdate= clienteService.findById(id);
		
		if (clienteupdate == null) {
			// Manejo cuando el cliente no se encuentra, podría ser un mensaje personalizado o simplemente not found
			return ResponseEntity.notFound().build();
		}

		clienteupdate.setApellido(cliente.getApellido());
		clienteupdate.setEmail(cliente.getEmail());
		clienteupdate.setNombre(cliente.getNombre());


		return ResponseEntity.ok().body(clienteService.save(clienteupdate));



	}

	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//204
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}

}
