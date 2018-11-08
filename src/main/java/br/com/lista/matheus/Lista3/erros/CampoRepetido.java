/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lista.matheus.Lista3.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author mathe
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoRepetido extends RuntimeException {

    public CampoRepetido(String msg) {
        super(msg);
    }
    
    
}
