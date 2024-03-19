package com.curso.javaspringboot.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.javaspringboot.dao.IClienteDao;
import com.curso.javaspringboot.entitys.Cliente;


@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

 
   
    @Override
    @Transactional
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }



    @SuppressWarnings("null")
    @Override
    @Transactional
    public Cliente findById(Long id) {
       
      return  clienteDao.findById(id).orElse(null);
    }



    @SuppressWarnings("null")
    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
       
        return clienteDao.save(cliente);
    }



    @SuppressWarnings("null")
    @Override
    @Transactional
    public void delete(Long id) {

         clienteDao.deleteById(id);
    }



   

}

   
