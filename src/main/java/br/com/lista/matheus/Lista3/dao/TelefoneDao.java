/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.dao;

import br.com.lista.matheus.Lista3.modelo.Telefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
@Repository
public interface TelefoneDao extends CrudRepository<Telefone, Integer>{
    
}
