/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.EditoraDao;
import br.com.lista.matheus.Lista3.dao.LivroDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;
import br.com.lista.matheus.Lista3.erros.CaracterInvalido;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.modelo.Editora;
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
public class Editoras {

    @Autowired
    EditoraDao editoraDao;

    @Autowired
    LivroDao livroDao;

    //verifica se o cnpj ja existe no sistema
    @RequestMapping(path = "/editoras/{cnpj}", method = RequestMethod.GET)
    public boolean cnpj(@PathVariable String cnpj) {
        Optional<Editora> cnpjInserido = editoraDao.findByCnpj(cnpj);
        if (cnpjInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/editoras/", method = RequestMethod.GET)
    public Iterable<Editora> listar() {
        return editoraDao.findAll();
    }

    @RequestMapping(path = "/editoras/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Editora inserir(@RequestBody Editora editora) {
        editora.setId(0);
        String cnpjs = editora.getCnpj();
        if (editora.getNome() == null || editora.getNome().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (editora.getCnpj() == null || editora.getCnpj().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (this.cnpj(cnpjs) == true) {
            throw new CaracterInvalido("O cnpj informado já está cadastrado!");

        }

        Editora editoraSalva = editoraDao.save(editora);
        return editoraSalva;
    }

    @RequestMapping(path = "/editoras/{id}", method = RequestMethod.GET)
    public Editora recuperar(@PathVariable int id) {
        Optional<Editora> findById = editoraDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/editoras/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Editora editora) {
        if (editoraDao.existsById(id)) {
            editora.setId(id);
            editoraDao.save(editora);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/editoras/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (editoraDao.existsById(id)) {
            editoraDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}
