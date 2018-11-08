/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.controle;

import br.com.lista.matheus.Lista3.dao.TelefoneDao;
import br.com.lista.matheus.Lista3.dao.UsuarioDao;
import br.com.lista.matheus.Lista3.erros.CampoObrigatorio;
import br.com.lista.matheus.Lista3.erros.CaracterInvalido;
import br.com.lista.matheus.Lista3.erros.NaoEncontrado;
import br.com.lista.matheus.Lista3.erros.RequisicaoRejeitada;
import br.com.lista.matheus.Lista3.modelo.Telefone;
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
public class Usuarios {

    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    TelefoneDao telefoneDao;

    @RequestMapping(path = "/usuarios/", method = RequestMethod.GET)
    public Iterable<Usuario> listar() {
        return usuarioDao.findAll();
    }

    @RequestMapping(path = "/bibliotecarios/verificarEmail/{email}", method = RequestMethod.GET)
    public boolean email(@PathVariable String email) {
        Optional<Usuario> emailInserido = usuarioDao.findByEmail(email);
        if (emailInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/bibliotecarios/verificarCpf{cpf}", method = RequestMethod.GET)
    public boolean cpf(@PathVariable String cpf) {
        Optional<Usuario> cpfInserido = usuarioDao.findByCpf(cpf);
        if (cpfInserido.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping(path = "/usuarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@RequestBody Usuario usuario) {
        usuario.setId(0);
        String emails = usuario.getEmail();
        String cpfs = usuario.getCpf();

        if (usuario.getNome() == null || usuario.getNome().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (usuario.getCpf() == null || usuario.getCpf().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (usuario.getEmail() == null || usuario.getEmail().equals("")) {
            throw new CampoObrigatorio("Este campo é obrigatorio!");

        } else if (this.email(emails) == true) {
            throw new CaracterInvalido("O email informado já está cadastrado!");

        } else if (this.cpf(cpfs) == true) {
            throw new CaracterInvalido("O cpf informado já está cadastrado!");

        } else {

            Usuario usuarioSalvo = usuarioDao.save(usuario);
            return usuarioSalvo;
        }
    }

    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.GET)
    public Usuario recuperar(@PathVariable int id) {
        Optional<Usuario> findById = usuarioDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Usuario usuario) {
        if (usuarioDao.existsById(id)) {
            usuario.setId(id);
            usuarioDao.save(usuario);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (usuarioDao.existsById(id)) {
            usuarioDao.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    ///////////////////////////pesquisas da api//////////////////////////////
    @RequestMapping(path = "/usuarios/pesquisar/nome/", method = RequestMethod.GET)

    public Iterable<Usuario> pesquisaPorNome(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {

        if (inicia != null) {
            return usuarioDao.findByNome(inicia);
        }
        if (contem != null) {
            return usuarioDao.findByNomeContaining(contem);
        }

        throw new RequisicaoRejeitada("Indique um dos 2 valores");

    }

    @RequestMapping(path = "/usuarios/pesquisar/cpf/", method = RequestMethod.GET)
    public Optional<Usuario> pesquisaPorCpf(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return usuarioDao.findByCpf(inicia);
        }
        if (contem != null) {
            return usuarioDao.findByCpfContaining(contem);
        }

        throw new RequisicaoRejeitada("Indique um dos 2 valores");

    }

    @RequestMapping(path = "/usuarios/pesquisar/email/", method = RequestMethod.GET)
    public Optional<Usuario> pesquisaPorEmail(
            @RequestParam(required = false) String inicia,
            @RequestParam(required = false) String contem) {
        if (inicia != null) {
            return usuarioDao.findByEmail(inicia);
        }
        if (contem != null) {
            return usuarioDao.findByEmailContaining(contem);
        }

        throw new RequisicaoRejeitada("Indique um dos 2 valores");

    }
////////////////////////////////////associação com telefones //////////////////////////////////

    @RequestMapping(path = "/usuarios/{id}/telefones/",
            method = RequestMethod.GET)
    public Iterable<Telefone> listarTelefone(@PathVariable int id) {
        return this.recuperar(id).getTelefones();

    }

    @RequestMapping(path = "/usuarios/{id}/telefones/",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Telefone inserirTelefone(@PathVariable int id,
            @RequestBody Telefone telefone) {
        telefone.setId(0);

        if (telefone.getNumero() == 0) {
            throw new CampoObrigatorio("Informe o numero");

        } else if (telefone.getTipo() == "" || telefone.getTipo() == null) {
            throw new CampoObrigatorio("Informe o tipo");

        } else {
            Telefone telefoneSalvo = telefoneDao.save(telefone);
            Usuario usuario = this.recuperar(id);
            usuario.getTelefones().add(telefoneSalvo);
            usuarioDao.save(usuario);
            return telefoneSalvo;
        }

    }

    @RequestMapping(path = "/usuarios/{id}/telefones/{id}",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizarTelefone(@PathVariable int idUsuario, @PathVariable int id, @RequestBody Telefone telefone) {
        if (telefoneDao.existsById(id)) {
            telefone.setId(id);
            telefoneDao.save(telefone);
        } else {
            throw new NaoEncontrado("Não encontrado");

        }
    }

    @RequestMapping(path = "/usuarios/{id}/telefones/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagarTelefone(@PathVariable int idUsuario,
            @PathVariable int id) {

        Telefone telefoneAchado = null;
        Usuario usuario = this.recuperar(idUsuario);
        List<Telefone> telefone = usuario.getTelefones();

        for (Telefone telefoneLista : telefone) {
            if (id == telefoneLista.getId()) {
                telefoneAchado = telefoneLista;
            }
        }
        if (telefoneAchado != null) {
            usuario.getTelefones().remove(telefoneAchado);
            usuarioDao.save(usuario);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
}
