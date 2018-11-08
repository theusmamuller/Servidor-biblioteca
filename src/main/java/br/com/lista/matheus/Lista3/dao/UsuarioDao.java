/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.dao;

import br.com.lista.matheus.Lista3.modelo.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Integer> {

    Iterable<Usuario> findByNome(String nome);

    Iterable<Usuario> findByNomeContaining(String nome);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailContaining(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByCpfContaining(String cpf);
}
