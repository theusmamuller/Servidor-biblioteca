/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.AutorDao;
import br.com.lista.matheus.Lista3.dao.LivroDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;
import br.com.lista.matheus.Lista3.erros.CaracterInvalido;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.modelo.Autor;
import br.com.lista.matheus.Lista3.modelo.Livro;
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
public class Autores {

    @Autowired
    AutorDao autorDao;

    @Autowired
    LivroDao livroDao;

    @RequestMapping(path = "/autores/{nome}", method = RequestMethod.GET)
    public boolean primeiroNome(@PathVariable String primeiroNome) {
        Optional<Autor> primeiroNomeInserido = autorDao.findByPrimeiroNome(primeiroNome);
        if (primeiroNomeInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/autores/{sobrenome}", method = RequestMethod.GET)
    public boolean segundoNome(@PathVariable String segundoNome) {
        Optional<Autor> segundoNomeInserido = autorDao.findBySegundoNome(segundoNome);
        if (segundoNomeInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/autores/", method = RequestMethod.GET)
    public Iterable<Autor> listar() {
        return autorDao.findAll();
    }

    @RequestMapping(path = "/autores/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Autor inserir(@RequestBody Autor autor) {
        autor.setId(0);
        String primeiroNome = autor.getPrimeiroNome();
        String segundoNome = autor.getSegundoNome();

        if (autor.getPrimeiroNome() == null || autor.getPrimeiroNome().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (autor.getSegundoNome() == null || autor.getSegundoNome().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (this.primeiroNome(primeiroNome) == true) {
            throw new CaracterInvalido("Nome já em uso!");

        } else if (this.segundoNome(segundoNome) == true) {
            throw new CaracterInvalido("Sobrenome em uso!");

        } else {

            Autor autorSalvo = autorDao.save(autor);
            return autorSalvo;
        }
    }

    @RequestMapping(path = "/autores/{id}", method = RequestMethod.GET)
    public Autor recuperar(@PathVariable int id) {
        Optional<Autor> findById = autorDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/autores/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Autor autor) {
        if (autorDao.existsById(id)) {
            autor.setId(id);
            autorDao.save(autor);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/autores/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (autorDao.existsById(id)) {
            autorDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    //buscando livros relacionados aos autores
    @RequestMapping(path = "/autores/{id}/livros/", method = RequestMethod.GET)
    public Optional<Autor> listarLivros(@PathVariable int id) {
        Optional<Autor> findById = autorDao.findById(id);
        if (findById.isPresent()) {
            return livroDao.findByAutores(findById.get());
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}
