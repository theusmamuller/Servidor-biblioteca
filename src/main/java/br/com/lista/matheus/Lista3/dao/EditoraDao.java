/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.dao;


import br.com.lista.matheus.Lista3.modelo.Editora;
import br.com.lista.matheus.Lista3.modelo.Livro;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
@Repository
public interface EditoraDao extends CrudRepository<Editora, Integer>{
    
    Optional<Editora> findById(int id);
    
     Optional<Editora> findByCnpj(String cnpj);
    Optional<Editora> findByCnpjContaining (String cnpj);
    

    
    
}
