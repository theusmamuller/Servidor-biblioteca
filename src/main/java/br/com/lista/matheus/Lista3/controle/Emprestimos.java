/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.EmprestimoDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.modelo.Emprestimo;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping(path = "/api")
public class Emprestimos {

    @Autowired
    EmprestimoDao emprestimoDao;

    @RequestMapping(path = "/emprestimos/", method = RequestMethod.GET)
    public Iterable<Emprestimo> listar() {
        return emprestimoDao.findAll();
    }

    @RequestMapping(path = "/emprestimos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo inserir(@RequestBody Emprestimo emprestimo) {
        emprestimo.setId(0);
        Date retirada = new Date();
        Date devolucao = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(devolucao);
        calendario.add(Calendar.DATE, +7);
        emprestimo.setRetirada(retirada);
        emprestimo.setPrevisaoDevolucao(devolucao);

        if (emprestimo.getUsuario() == null || emprestimo.getUsuario().equals("")) {
            throw new CampoObrigatorio("Por favor, insira o usuario.");

        } else if (emprestimo.getBibliotecario() == null || emprestimo.getBibliotecario().equals("")) {
            throw new CampoObrigatorio("Por favor, insira o bibliotecario.");

        } else if (emprestimo.getLivro() == null || emprestimo.getLivro().equals("")) {
            throw new CampoObrigatorio("Por favor, insira o livro.");

        } else {

            Emprestimo emprestimoSalvo = emprestimoDao.save(emprestimo);
            return emprestimoSalvo;
        }
    }

    @RequestMapping(path = "/emprestimos/{id}", method = RequestMethod.GET)
    public Emprestimo recuperar(@PathVariable int id) {
        Optional<Emprestimo> findById = emprestimoDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/emprestimos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Emprestimo emprestimo) {
        if (emprestimoDao.existsById(id)) {
            emprestimo.setId(id);
            emprestimoDao.save(emprestimo);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/emprestimos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (emprestimoDao.existsById(id)) {
            emprestimoDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}
