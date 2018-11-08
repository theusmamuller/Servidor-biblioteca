/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.dao;

import br.com.lista.matheus.Lista3.modelo.Autor;
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
public interface AutorDao extends CrudRepository<Autor, Integer> {
    
     Optional<Autor> findById(int id);
    
    Optional<Autor> findByPrimeiroNome(String primeiroNome);

    Optional<Autor> findByPrimeiroNomeContaining(String primeiroNome);

    Optional<Autor> findBySegundoNome(String segundoNome);

    Optional<Autor> findBySegundoNomeContaining(String segundoNome);
    
    Optional<Autor> findByLivro(Livro livro);
    
    
    
}
