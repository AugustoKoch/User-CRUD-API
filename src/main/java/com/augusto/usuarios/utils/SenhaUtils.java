package com.augusto.usuarios.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.augusto.usuarios.utils.StringUtils.*;
import static com.augusto.usuarios.utils.StringUtils.contemEspecial;

public class SenhaUtils {
    public static void validarSenha(String senha) {

        if (senha.length() < 8){
            throw new RuntimeException("A senha deve possuir 8 caracteres ou mais!");
        }
        validarCaracteresSenha(senha);
    }

    public static void validarCaracteresSenha(String senha){
        if (!contemMaiuscula(senha) || !contemMinuscula(senha) || !contemNumero(senha) || !contemEspecial(senha)) {
            throw new RuntimeException("A senha deve conter letras maisculas, minusculas, números e caracteres especiais");
        }
    }
}