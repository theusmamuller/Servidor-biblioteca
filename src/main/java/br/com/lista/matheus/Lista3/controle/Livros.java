/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.AutorDao;
import br.com.lista.matheus.Lista3.dao.EditoraDao;
import br.com.lista.matheus.Lista3.dao.LivroDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.erros.RequisicaoRejeitada;
import br.com.lista.matheus.Lista3.modelo.Autor;
import br.com.lista.matheus.Lista3.modelo.Editora;
import br.com.lista.matheus.Lista3.modelo.Livro;
import br.com.lista.matheus.Lista3.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping(path = "/api")
public class Livros {

    @Autowired
    LivroDao livroDao;

    @Autowired
    AutorDao autorDao;

    @Autowired
    EditoraDao editoraDao;

    @RequestMapping(path = "/livros/", method = RequestMethod.GET)
    public Iterable<Livro> listar() {
        return livroDao.findAll();
    }

    @RequestMapping(path = "/livros/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Livro inserir(@RequestBody Livro livro) {
        livro.setId(0);

        if (livro.getTitulo() == null || livro.getTitulo().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (livro.getAnoPublicacao() == 0) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else {

            Livro livroSalvo = livroDao.save(livro);
            return livroSalvo;
        }
    }

    @RequestMapping(path = "/livros/{id}", method = RequestMethod.GET)
    public Livro idLivro(@PathVariable int id) {
        Optional<Livro> findById = livroDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/livros/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Livro livro) {
        if (livroDao.existsById(id)) {
            livro.setId(id);
            livroDao.save(livro);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/livros/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (livroDao.existsById(id)) {
            livroDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    ///////////////////// associações por sub-url /////////////////////////
    @RequestMapping(path = "/livros/{id}/editoras/",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void inserirEditora(@PathVariable int id, @RequestBody Editora editora) {

        Livro livros = this.idLivro(id);
        livros.getEditoras().add(editora);
        livroDao.save(livros);
    }

    @RequestMapping(path = "/livros/{id}/editoras/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public List<Editora> listaEditora(@PathVariable int id) {
        Livro livros = this.idLivro(id);
        return livros.getEditoras();

    }

    @RequestMapping(path = "/livros/{id}/autores/",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void inserirAutor(@PathVariable int id, @RequestBody Autor autores) {

        Livro livros = this.idLivro(id);
        livros.getAutores().add(autores);
        livroDao.save(livros);
    }

    @RequestMapping(path = "/livros/{id}/autores/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public List<Autor> listaAutor(@PathVariable int id) {
        Livro livros = this.idLivro(id);
        return livros.getAutores();
    }

    /////////////////////// pesquisas API ////////////////////////////
    @RequestMapping(path = "/livros/pesquisar/titulo", method = RequestMethod.GET)

    public Optional<Livro> pesquisarPorTitulo(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {

        if (inicia != null) {
            return livroDao.findByTitulo(inicia);
        }
        if (contem != null) {
            return livroDao.findByTituloContaining(contem);
        }

        throw new RequisicaoRejeitada("Indique um dos 2 valores");

    }

    @RequestMapping(path = "/livros/pesquisar/ano/", method = RequestMethod.GET)
    public Optional<Livro> pesquisaPorAnoPublicacao(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return livroDao.findByAnoPublicacao(inicia);
        }
        if (contem != null) {
            return livroDao.findByAnoPublicacaoContaining(contem);
        }

        throw new RequisicaoRejeitada("Indique um dos 2 valores");

    }

}
