package com.example.omdb.servicos;

public interface IConverteDados {
    <T> T obterDados(String json, Class <T> classe);
}
