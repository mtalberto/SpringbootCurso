package com.curso.javaspringboot.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
		try{

			 cliente = clienteService.findById(id);
			

		}
		
		
		//capturar el erro del servidor
		catch(DataAccessException e){
			response.put("mensaje", "eror al realizar la consula en la base de datos");
			response.put("error", e.getMessage().concat(": ".concat( e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);


		}
		//capturar el error del id
		if(cliente == null){
			response.put("mensaje", "El cliente ID :" .concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED) // 201 create
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {

		Cliente newCliente= null;
		Map<String,Object> response=new HashMap<>();

		try {
			newCliente= clienteService.save(cliente);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar al insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		response.put("mensaje", "el cliente ha sido creado con éxito!");
		response.put("cliente", newCliente);


		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

	@PutMapping("/clientes/{id}")
	
	public ResponseEntity<?> update(@RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteSinActualizar= clienteService.findById(id);
		Cliente clienteActualizado=null;

		Map<String,Object> response=new HashMap<>();

		if(clienteSinActualizar==null){
			response.put("mensaje", "Error no se pudo editar, el cliente ID ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}

		try {
			clienteSinActualizar.setApellido(cliente.getApellido());
			clienteSinActualizar.setEmail(cliente.getEmail());
			clienteSinActualizar.setNombre(cliente.getNombre());


			clienteActualizado= clienteService.save(clienteSinActualizar);

		} catch (DataAccessException e ) {
			response.put("mensaje", "error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		response.put("mensaje ", "el cliente ha sido actualizado");
		response.put("cliente", clienteActualizado);




		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);



	}

	@DeleteMapping("/clientes/{id}")
	
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String,Object> response = new HashMap<>();
		Cliente cliente = clienteService.findById(id);
		try {
		if(cliente==null){
			response.put("mensaje", "Error: no se pudo eliminar, el cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}	
		
			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "el cliente eliminado con exito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

}
