package com.example.omdb.util;

public class Util {
    public static String formataSoutTitulo(String mensagem) {
        String asteriscos = "*".repeat(mensagem.length());
        return """
                %s
                %s
                %s
                """.formatted(asteriscos, mensagem, asteriscos);
    }
}
