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
public interface LivroDao extends CrudRepository<Livro, Integer>{
  
    Optional<Livro> findById(int id);
    Optional<Livro> findByIdContaining (int id);
    
    Optional<Livro> findByTitulo(String titulo);
    Optional<Livro> findByTituloContaining (String titulo);
    
    Optional<Livro> findByAnoPublicacao(String ano);
    Optional<Livro> findByAnoPublicacaoContaining (String ano);
    
    Optional<Autor> findByAutores(Autor autores);
    
    Optional<Editora> findByEditoras(Editora editoras);
    
}
