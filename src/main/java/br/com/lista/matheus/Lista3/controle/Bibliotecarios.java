/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.BibliotecarioDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;

import br.com.lista.matheus.Lista3.erros.CaracterInvalido;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.modelo.Bibliotecario;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping(path = "/api")
public class Bibliotecarios {

    @Autowired
    BibliotecarioDao bibliotecarioDao;

    @RequestMapping(path = "/bibliotecarios/", method = RequestMethod.GET)
    public Iterable<Bibliotecario> listar() {
        return bibliotecarioDao.findAll();
    }
    
//verifica se o email inserido ja existe no sistema
    @RequestMapping(path = "/bibliotecarios/{email}", method = RequestMethod.GET)
    public boolean email(@PathVariable String email) {
        Optional<Bibliotecario> emailInserido = bibliotecarioDao.findByEmail(email);
        if (emailInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/bibliotecarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Bibliotecario inserir(@RequestBody Bibliotecario bibliotecario) {
        bibliotecario.setId(0);
        String emails = bibliotecario.getEmail();
        // verificando se todos os campos obrigatorios foram preenchidos
        if (bibliotecario.getNome() == null || bibliotecario.getNome().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (bibliotecario.getEmail() == null || bibliotecario.getEmail().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (bibliotecario.getSenha() == null || bibliotecario.getSenha().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (bibliotecario.getSenha().length() < 8) {
            throw new CaracterInvalido("A senha deve ter no minimo 08 caracteres!");

        } else if (this.email(emails) == true) {
            throw new CaracterInvalido("O email informado já está cadastrado!");

        } else {

            Bibliotecario bibliotecarioSalvo = bibliotecarioDao.save(bibliotecario);
            return bibliotecarioSalvo;

        }
    }

    @RequestMapping(path = "/bibliotecarios/{id}", method = RequestMethod.GET)
    public Bibliotecario recuperar(@PathVariable int id) {
        Optional<Bibliotecario> findById = bibliotecarioDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/bibliotecarios/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Bibliotecario bibliotecario) {
        if (bibliotecarioDao.existsById(id)) {
            bibliotecario.setId(id);
            bibliotecarioDao.save(bibliotecario);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/bibliotecarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (bibliotecarioDao.existsById(id)) {
            bibliotecarioDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}
