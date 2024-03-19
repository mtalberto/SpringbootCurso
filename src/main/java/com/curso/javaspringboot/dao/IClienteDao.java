package com.curso.javaspringboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.curso.javaspringboot.entitys.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
