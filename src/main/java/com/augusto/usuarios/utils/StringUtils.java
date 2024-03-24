package com.augusto.usuarios.utils;

public class StringUtils {
    public static boolean contemMaiuscula(String senha) {
        return senha.chars().anyMatch(Character::isUpperCase);
    }

    public static boolean contemMinuscula(String senha) {
        return senha.chars().anyMatch(Character::isLowerCase);
    }

    public static boolean contemNumero(String senha) {
        return senha.chars().anyMatch(Character::isDigit);
    }

    public static boolean contemEspecial(String senha) {
        return senha.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch));
    }
}
