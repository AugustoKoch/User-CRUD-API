package com.augusto.usuarios.dto;

import com.augusto.usuarios.model.Usuario;
import lombok.Getter;

@Getter
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;


    public Usuario transformaParaObjeto(){
        return new Usuario(nome, email, senha);
    }
}
