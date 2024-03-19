package com.curso.javaspringboot.services;

import java.util.List;

import com.curso.javaspringboot.entitys.Cliente;



public interface IClienteService {

    public List<Cliente> findAll();

    public Cliente findById(Long id);

    public Cliente save(Cliente cliente);

    public void delete (Long id);
}
