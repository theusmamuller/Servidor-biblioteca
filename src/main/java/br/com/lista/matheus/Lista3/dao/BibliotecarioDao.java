/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.dao;

import br.com.lista.matheus.Lista3.modelo.Bibliotecario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
  
    @Repository
    public interface BibliotecarioDao extends CrudRepository<Bibliotecario, Integer>{
        
        Optional<Bibliotecario> findByEmail(String email);
        Iterable<Bibliotecario> findByEmailContaining (String email);
    
}
   
